package top.blackcat.sm.secret.aspect;


import top.blackcat.sm.secret.annotation.SecretEncrypt;
import top.blackcat.sm.secret.result.Result;
import top.blackcat.sm.secret.service.SecretService;
import com.alibaba.fastjson.JSON;
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
    public void secretAspect() {
    }

    /***
     * 配置环绕通知,使用在方法aspect()上注册的切入点
     * 环绕通知：环绕目标方式执行
     * @param pjp
     * @return
     */
    @Around("secretAspect()")
    public Object around(ProceedingJoinPoint pjp) {
        long startTime = System.currentTimeMillis();
        Object result = null;

        try {
            // 获取请求参数 和请求值
            Object[] values = pjp.getArgs();
            String[] names = ((CodeSignature) pjp.getSignature()).getParameterNames();
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            Method method = methodSignature.getMethod();
            // 获取注解
            SecretEncrypt secretEncrypt = method.getAnnotation(SecretEncrypt.class);
            // 是否忽略加解密
            boolean ignoreEncrypt = secretEncrypt.ignoreEncrypt();
            // 加解密字段
            String[] optFields = secretEncrypt.optField();
            int optType = secretEncrypt.type();
            // 注解字段和 配置字段合并
            List<String> fieldsList = getUniqueFields(optFields, optField);
            Object[] fields = fieldsList.toArray();
            // 是否忽略加解密
            if (!ignoreEncrypt) {
                // 加密
                if (optType < 2) {
                    encryptValues(method, values, names, fields);
                }
                // 执行方法
                result = pjp.proceed(values);
                // 解密
                if (optType != 1) {
                    decryptValues(result, fields);
                }
            }
        } catch (Throwable e) {
            log.error(e.toString());
        } finally {
            long endTime = System.currentTimeMillis();
            long excTime = (endTime - startTime);
            System.out.println("耗时：" + excTime);
            return result;
        }
    }


    private List<String> getUniqueFields(String[] optFields, String optField) {
        Set<String> set = new HashSet<>(Arrays.asList(optFields));
        set.addAll(Arrays.asList(optField.split(",")));
        return new ArrayList<>(set);
    }

    /***
     * 加密
     * @param method
     * @param values
     * @param names
     * @param fields
     */
    private void encryptValues(Method method, Object[] values, String[] names, Object[] fields) {
        for (int i = 0; i < values.length; i++) {
            // String 参数
            if (values[i] instanceof String) {
                for (int j = 0; j < fields.length; j++) {
                    if (fields[j].toString().equals(names[i])) {
                        String paramValue = values[i].toString();
                        paramValue = postSecretApi(1, paramValue);
                        values[i] = paramValue;
                        break;
                    }
                }
                // RequestBody 注解的 json 参数
            } else if (Arrays.stream(method.getParameterAnnotations()[i]).anyMatch(item -> item.annotationType().equals(RequestBody.class))) {
                JSONObject data2 = JSONObject.parseObject(JSON.toJSONString(values[i]));
                for (String key : data2.keySet()) {
                    for (int j = 0; j < fields.length; j++) {
                        String paramValue = data2.getString(key);
                        if (fields[j].toString().equals(key) && paramValue instanceof String) {
                            paramValue = postSecretApi(1, paramValue);
                            data2.put(key, paramValue);
                            break;
                        }
                    }
                }
                values[i] = JSONObject.parseObject(data2.toString(), values[i].getClass());
            }
        }
    }

    /***
     * 解密
     * @param result
     * @param fields
     */
    private void decryptValues(Object result, Object[] fields) {
        // 如果是 Result 风格
        if (result instanceof Result) {
            Result r = (Result) result;
            Object rdata = r.getData();
            handleDecryptValue(rdata, fields);
        } else {
            handleDecryptValue(result, fields);
        }
    }

    /***
     * 解密处理
     * @param result
     * @param fields
     */
    private void handleDecryptValue(Object result, Object[] fields) {
        if (result instanceof List) {
            handleDecryptList((List) result, fields);
        } else if (result instanceof Map) {
            handleDecryptMap((Map) result, fields);
        } else {
            handleDecryptObj(result, fields);
        }
    }

    /***
     * 处理 list
     * @param list
     * @param fields
     */
    private void handleDecryptList(List list, Object[] fields) {
        for (int i = 0; i < list.size(); i++) {
            Object record = list.get(i);
            if (record instanceof Map) {
                handleDecryptMap((Map) record, fields);
            }
            list.set(i, record);
        }
    }

    /***
     * 处理 map
     * @param map
     * @param fields
     */
    private void handleDecryptMap(Map map, Object[] fields) {
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object value = map.get(key);
            if (value instanceof List) {
                handleDecryptList((List) value, fields);
            } else if (value instanceof String) {
                for (int i = 0; i < fields.length; i++) {
                    if (fields[i].toString().equals(key)) {
                        String paramValue = value.toString();
                        paramValue = postSecretApi(2, paramValue);
                        map.put(key, paramValue);
                        break;
                    }
                }
            } else {
                handleDecryptObj(value, fields);
            }
        }
    }


    /***
     * 处理 object
     * @param object
     * @param fields
     */
    private void handleDecryptObj(Object object, Object[] fields) {
        JSONObject jsonData = JSONObject.parseObject(JSON.toJSONString(object));
        for (String key : jsonData.keySet()) {
            Object value = jsonData.get(key);
            if (value instanceof String) {
                for (int i = 0; i < fields.length; i++) {
                    if (fields[i].toString().equals(key)) {
                        String paramValue = value.toString();
                        paramValue = postSecretApi(2, paramValue);
                        jsonData.put(key, paramValue);
                        break;
                    }
                }
            }
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
