package top.blackcat.sm.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Title: 写注释，下班早
 * @Desc: 生成实体类
 * @Author: Jelly
 * @Date: 2023/11/24
 * @Version V1.0
 */
public class GenEntityMysql {
    private static final GenEntityMysql INSTANCE = new GenEntityMysql();

    private String tableName; // 表名
    private String[] colNames; // 列名数组
    private String[] colTypes; // 列名类型数组
    private int[] colSizes; // 列名大小数组
    private String[] colDescs; // 列名描述数组
    private String[] colKeys; // 主键
    private boolean needUtil = true; // 是否需要导入包java.util.*
    private boolean needSql = true; // 是否需要导入包java.sql.*
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String SQL = "SELECT * FROM "; // 数据库操作
    private DatabaseMetaData db;
    // TODO 需要修改的地方
    private static final String URL = "jdbc:mysql://localhost:3306/secret-sm?useUnicode=true&characterEncoding=UTF-8";
    private static final String NAME = "root";
    private static final String PASS = "fulong";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private String packageOutPath = "top.blackcat.sm.secret.entity"; // 指定实体生成所在包的路径
    private String authorName = "jelly"; // 作者名字
    private String path = "E:\\github-rep/secret-sm/secret-sm-server/src/main/java/top/blackcat/sm/secret/entity"; // 实体文件生成的地方
    private String iftable = ""; // 为空就是导入全库，写上表名字就是单表

    /**
     * 类的构造方法
     */
    private GenEntityMysql() {
    }

    /**
     * @return
     * @description 生成class的所有内容
     */
    private String parse() throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + packageOutPath + ";\r\n");
        sb.append("\r\n");
        // 判断是否导入工具包
        if (needUtil) {
            sb.append("import java.util.Date;\r\n");
        }
        if (needSql) {
            sb.append("import java.sql.*;\r\n");
        }
        sb.append("import java.sql.Timestamp;\r\n");
        sb.append("import javax.persistence.Table;\r\n");
        sb.append("import javax.persistence.Entity;\r\n");
        sb.append("import javax.persistence.GeneratedValue;\r\n");
        sb.append("import javax.persistence.Id;\r\n");
        sb.append("import org.hibernate.annotations.GenericGenerator;\r\n");
        sb.append("import java.io.Serializable;\r\n");

        // 注释部分
        sb.append("/**\r\n");
        sb.append(" * table name:  " + tableName + "\r\n");
        sb.append(" * author name: " + authorName + "\r\n");
        sb.append(" * create time: " + SDF.format(new Date()) + "\r\n");
        sb.append(" */ \r\n");
        sb.append("@Entity \r\n");
        sb.append("@Table(name=\"" + tableName + "\") \r\n");
        // 实体部分
        sb.append("public class " + getTransStr(tableName, true) + " implements Serializable {\r\n\r\n");
        processAllAttrs(tableName, sb); // 属性
        return sb.toString();
    }

    private void processAllAttrs(String tableName, StringBuffer sb) throws SQLException {
        ResultSet primaryKeyResultSet = db.getPrimaryKeys("", null, tableName);
        List<String> primaryKeyColumnNameList = new ArrayList<>();
        while (primaryKeyResultSet.next()) {
            primaryKeyColumnNameList.add(primaryKeyResultSet.getString("COLUMN_NAME"));
        }

        for (int i = 0; i < colNames.length; i++) {
            sb.append("\t /**" + colDescs[i] + "**/\r\n");
            for (String primaryKeyColumnName : primaryKeyColumnNameList) {
                if (primaryKeyColumnName.equals(colNames[i])) {
                    if (colSizes[i] == 32) {
                        sb.append("\t@Id\n");
                        sb.append("\t@GeneratedValue(generator = \"uuid\")\r\n");
                        sb.append("\t@GenericGenerator(name = \"uuid\", strategy = \"uuid\")\r\n");
                    } else {
                        sb.append("\t@Id\r\n");
                    }
                }
            }
            sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " " + getTransStr(colNames[i], false) + ";\r\n");
        }
    }

    private void processAllMethod(StringBuffer sb) {
        for (int i = 0; i < colNames.length; i++) {
            sb.append("\tpublic void set" + getTransStr(colNames[i], true) + "(" + sqlType2JavaType(colTypes[i]) + " "
                    + getTransStr(colNames[i], false) + "){\r\n");
            sb.append("\t\tthis." + getTransStr(colNames[i], false) + "=" + getTransStr(colNames[i], false) + ";\r\n");
            sb.append("\t}\r\n");
            sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get" + getTransStr(colNames[i], true) + "(){\r\n");
            sb.append("\t\treturn " + getTransStr(colNames[i], false) + ";\r\n");
            sb.append("\t}\r\n");
        }
    }

    private static String initCap(String str) {
        str = str.toLowerCase();
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z')
            ch[0] = (char) (ch[0] - 32);
        return new String(ch);
    }

    public static String getTransStr(String before, boolean firstChar2Upper) {
        before = before.toLowerCase();
        // 不带"_"的字符串,则直接首字母大写后返回
        if (!before.contains("_"))
            return firstChar2Upper ? initCap(before) : before;
        String[] strs = before.split("_");
        StringBuffer after = null;
        if (firstChar2Upper) {
            after = new StringBuffer(initCap(strs[0]));
        } else {
            after = new StringBuffer(strs[0]);
        }
        for (int i = 1; i < strs.length; i++)
            after.append(initCap(strs[i]));
        return after.toString();
    }

    private String sqlType2JavaType(String sqlType) {
        if (sqlType.equalsIgnoreCase("bit")) {
            return "Boolean";
        } else if (sqlType.equalsIgnoreCase("tinyint")) {
            return "Byte";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            return "Short";
        } else if (sqlType.equalsIgnoreCase("int") || sqlType.equalsIgnoreCase("integer")) {
            return "Integer";
        } else if (sqlType.equalsIgnoreCase("bigint")) {
            return "Long";
        } else if (sqlType.equalsIgnoreCase("float")) {
            return "Float";
        } else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
                || sqlType.equalsIgnoreCase("smallmoney") || sqlType.equalsIgnoreCase("double")) {
            return "Double";
        } else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
                || sqlType.equalsIgnoreCase("text") || sqlType.equalsIgnoreCase("longtext")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("datetime")) {
            return "Date";
        } else if (sqlType.equalsIgnoreCase("image")) {
            return "Blod";
        }
        return null;
    }

    private void generate() throws Exception {
        // 与数据库的连接
        Connection con;
        PreparedStatement pStemt = null;
        Class.forName(DRIVER);
        con = DriverManager.getConnection(URL, NAME, PASS);
        System.out.println("connect database success...");
        // 获取数据库的元数据
        db = con.getMetaData();
        // 从元数据中获取到所有的表名
        ResultSet rs = db.getTables(null, "%", StringUtils.isBlank(iftable) ? "%" : iftable, new String[]{"TABLE"});

        String tableSql;
        PrintWriter pw = null;
        while (rs.next()) {
            tableName = rs.getString(3);
            tableSql = SQL + tableName;
            pStemt = con.prepareStatement(tableSql);
            ResultSetMetaData rsmd = pStemt.getMetaData();
            ResultSet rs1 = db.getColumns(null, "%", tableName, "%");
            rs1.last();
            int size = rs1.getRow();
            rs1.beforeFirst();
            colNames = new String[size];
            colTypes = new String[size];
            colSizes = new int[size];
            colDescs = new String[size];
            int i = 0;
            while (rs1.next()) {
                colNames[i] = rs1.getString("COLUMN_NAME");
                colTypes[i] = rs1.getString("TYPE_NAME");
                colDescs[i] = rs1.getString("REMARKS");
                if (colTypes[i].equalsIgnoreCase("datetime")) {
                    needUtil = true;
                }
                if (colTypes[i].equalsIgnoreCase("image") || colTypes[i].equalsIgnoreCase("text")) {
                    needSql = true;
                }
                colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
                i++;
            }

            // 解析生成class的所有内容
            String content = parse();
            // 输出生成文件
            File directory = new File("");
            String outputPath = path + "/" + packageOutPath.replace(".", "/")
                    + "/" + getTransStr(tableName, true) + ".java";
            File destFile = new File(outputPath);
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
                try {
                    destFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            FileWriter fw = new FileWriter(outputPath);
            pw = new PrintWriter(fw);
            pw.println(content);
            pw.flush();
            System.out.println("create class >>>>> " + tableName);
        }
        if (pw != null) {
            pw.close();
        }
    }

    /**
     * @param args
     * @description 执行方法
     */
    public static void main(String[] args) {
        try {
            INSTANCE.iftable = "sign_key";
            INSTANCE.generate();
            System.out.println("generate classes success!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}