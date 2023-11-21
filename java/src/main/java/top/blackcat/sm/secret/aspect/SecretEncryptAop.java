package top.blackcat.sm.secret.aspect;


import org.springframework.util.StringUtils;
import top.blackcat.sm.secret.annotation.SecretEncrypt;
import top.blackcat.sm.secret.result.Result;
import top.blackcat.sm.secret.service.SecretService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Jelly
 * @create 2021/3/27 17:01
 * @desc 加解密切面 aop
 **/
@Aspect
@Slf4j
@Order(2)
@Component
public class SecretEncryptAop {

    @Autowired
    private SecretService secretService;


    // 加密字段 例如 phone realName idCard 匹配上就会操作 谨慎配置一定要配置 确定要加解密的字段
    @Value("${application.secret.opt-field}")
    private String optField;

    /***
     * 切点 注解 @SecretEncrypt
     */
    @Pointcut("within(top.blackcat..*) && @annotation(top.blackcat.sm.secret.annotation.SecretEncrypt)")
    public void controllerAspect() {
    }

    /***
     * 配置环绕通知,使用在方法aspect()上注册的切入点
     * 环绕通知：环绕目标方式执行
     * @param pjp
     * @return
     */
    @Around("controllerAspect()")
    public Object around(ProceedingJoinPoint pjp) {
        //开始时间
        long startTime = System.currentTimeMillis();
        Result result = null;
        // 执行目标方法
        try {
            // 前置通知
            // 过滤掉 file 会有 json 转化问题
            Object[] values = Arrays.stream(pjp.getArgs()).filter(e -> !(e instanceof MultipartFile) && !(e instanceof MultipartFile[])).toArray();
            String[] names = ((CodeSignature) pjp.getSignature()).getParameterNames();
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            Method method = methodSignature.getMethod();
            SecretEncrypt secretEncrypt = method.getAnnotation(SecretEncrypt.class);
            // 是否需要加解密
            boolean ignoreEncrypt = secretEncrypt.ignoreEncrypt();
            String[] optFields = secretEncrypt.optField();
            // 0 加解密 1 加密  2 解密
            int optType = secretEncrypt.type();
            String[] fieldsYml = optField.split(",");

            // 将 yml和注解的 字段合并 去重
            Set<String> set = new HashSet<>(Arrays.asList(optFields));
            set.addAll(Arrays.asList(fieldsYml));
            List<String> fieldsList = new ArrayList<>(set);
            Object[] fields = fieldsList.toArray();

            // 1.参数模式
            // 2.JSON模式
            // 如果忽略 加解密 不处理
            if (!ignoreEncrypt) {
                if (optType < 2) {
                    //   匹配参数
                    for (int i = 0; i < values.length; i++) {
                        if (values[i] instanceof String) {
                            //   匹配加密参数
                            for (int j = 0; j < fields.length; j++) {
                                String param = names[i];
                                String paramEncrypt = fields[j].toString();
                                if (param.equals(paramEncrypt)) {
                                    String paramValue = values[i].toString();
                                    paramValue = postSecretApi(1, paramValue);
                                    values[i] = paramValue;
                                }
                            }
                            //   匹配json
                        } else if (Arrays.stream(method.getParameterAnnotations()[i]).anyMatch(item -> item.annotationType().equals(RequestBody.class))) {
                            JSONObject data2 = JSONObject.parseObject(JSON.toJSONString(values[i]));
                            for (String key : data2.keySet()) {
                                for (int j = 0; j < fields.length; j++) {
                                    String paramValue = data2.getString(key);
                                    if (fields[j].toString().equals(key) && paramValue instanceof String) {
                                        paramValue = postSecretApi(1, paramValue);
                                        data2.put(key, paramValue);
                                    }
                                }
                            }
                            values[i] = JSONObject.parseObject(data2.toString(), values[i].getClass());
                        }
                    }
                }
                result = (Result) pjp.proceed(values);
                if (optType != 1) {
                    Object value1 = result.getData();
                    try {
                        // 1、 list里面放对象
                        if (value1 instanceof List) {
                            JSONArray dataArray = JSONArray.parseArray(JSON.toJSONString(value1));
                            for (int i = 0; i < dataArray.size(); i++) {
                                JSONObject data2 = (JSONObject) dataArray.get(i);
                                for (String key2 : data2.keySet()) {
                                    Object value2 = data2.get(key2);
                                    if (value2 instanceof String) {
                                        for (int j = 0; j < fields.length; j++) {
                                            if (fields[j].toString().equals(key2)) {
                                                String paramValue = value2.toString();
                                                paramValue = postSecretApi(2, paramValue);
                                                data2.put(key2, paramValue);
                                            }
                                        }
                                    }
                                }
                                dataArray.set(i, data2);
                            }
                            value1 = dataArray;
                            // 2、 Map对象 show框架查询出来的
                        } else if (value1 instanceof Map) {
                            Map data = (Map) value1;
                            Iterator<String> iterator = data.keySet().iterator();
                            while (iterator.hasNext()) {
                                String key = iterator.next();
                                if (data.get(key) != null) {
                                    //    list
                                    if (data.get(key) instanceof List) {
                                        List list = (List) data.get(key);
                                        for (int i = 0; i < list.size(); i++) {
                                            Map record = (Map) list.get(i);
                                            Iterator<String> iteratorRecord = record.keySet().iterator();
                                            while (iteratorRecord.hasNext()) {
                                                String keyRecord = iteratorRecord.next();
                                                for (int j = 0; j < fields.length; j++) {
                                                    if (fields[j].toString().equals(keyRecord) && record.get(keyRecord) instanceof String) {
                                                        String paramValue = record.get(keyRecord).toString();
                                                        paramValue = postSecretApi(2, paramValue);
                                                        record.put(keyRecord, paramValue);
                                                    }
                                                }
                                            }
                                            list.set(i, record);
                                        }
                                        data.put(key, list);
                                        value1 = data;
                                    } else {
                                        //  map
                                        JSONObject data2 = JSONObject.parseObject(JSON.toJSONString(value1));
                                        for (String key2 : data2.keySet()) {
                                            Object value2 = data2.get(key2);
                                            if (value2 instanceof String) {
                                                for (int j = 0; j < fields.length; j++) {
                                                    if (fields[j].toString().equals(key2)) {
                                                        String paramValue = value2.toString();
                                                        paramValue = postSecretApi(2, paramValue);
                                                        data2.put(key2, paramValue);
                                                    }
                                                }
                                            }
                                        }
                                        value1 = data2;
                                    }
                                }
                            }
                            //  3、 obj对象 其它情况
                        } else {
                            JSONObject data2 = JSONObject.parseObject(JSON.toJSONString(value1));
                            for (String key2 : data2.keySet()) {
                                Object value2 = data2.get(key2);
                                if (value2 instanceof String) {
                                    for (int j = 0; j < fields.length; j++) {
                                        if (fields[j].toString().equals(key2)) {
                                            String paramValue = value2.toString();
                                            paramValue = postSecretApi(2, paramValue);
                                            data2.put(key2, paramValue);
                                        }
                                    }
                                }
                            }
                            value1 = data2;
                        }
                    } catch (Exception e) {
                        System.out.println("不是JSONObject格式，不能转化JSONObject格式的不处理");
                    }
                    result.setData(value1);
                }
            } else {
                result = (Result) pjp.proceed(values);
            }
        } catch (Throwable e) {
            log.error(e.toString());
        } finally {
            //后置通知
            long endTime = System.currentTimeMillis();
            long excTime = (endTime - startTime);
            System.out.println("耗时：" + excTime);
            return result;
        }

    }

    /***
     *
     * @param type 加密还是解密
     * @param value 加密还是解密的文本
     * @return
     */
    public String postSecretApi(int type, String value) {
        try {
            if (1 == type) {
                value = secretService.encrypt(UUID.randomUUID().toString().replace("-", ""), value);
                System.out.println("加密值：" + value);
            } else {
                // 判断是否是 base64字符串
                value = secretService.decrypt(UUID.randomUUID().toString().replace("-", ""), value);
                System.out.println("解密值：" + value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return value;
        }
    }

}
