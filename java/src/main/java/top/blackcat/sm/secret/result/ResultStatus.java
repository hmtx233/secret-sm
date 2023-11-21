package top.blackcat.sm.secret.result;

public final class ResultStatus {
    public class Code {
        public static final String SUCCESS = "200";
        public static final String FAILURE = "0";

        public static final String NO_LOGIN = "300";
//        平谷
        public static final String PG_NO_LOGIN = "301";
        public static final String NO_ADMIN_AUTH= "606";


    }

    public class Msg {
        public static final String SUCCESS = "成功";
        public static final String FAILURE = "失败";
        public static final String NO_LOGIN = "没有登陆";
        public static final String NO_ADMIN_AUTH= "没有后台权限";


    }
}
