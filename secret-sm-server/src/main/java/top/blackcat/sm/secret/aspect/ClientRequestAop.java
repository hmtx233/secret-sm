package top.blackcat.sm.secret.aspect;


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
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import top.blackcat.sm.secret.entity.SysLog;
import top.blackcat.sm.secret.mapper.SysLogMapper;
import top.blackcat.sm.secret.result.Result;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

/**
 * @Title: 写注释，下班早
 * @Desc: 记录客户端请求
 * @Author: Jelly
 * @Date: 2023/11/24
 * @Version V1.0
 */
@Component
@Aspect
@Slf4j
@Order(1)
public class ClientRequestAop {

    @Autowired
    private SysLogMapper sysLogMapper;


    /***
     * 切点 带有日志注解 @RecordClientRequest
     */
    @Pointcut("within(top.blackcat.sm.secret..*) && @annotation(top.blackcat.sm.secret.annotation.RecordClientRequest)")
    public void controllerAspect() {
    }

    /***
     * 配置环绕通知,使用在方法aspect()上注册的切入点
     * 环绕通知：环绕目标方式执行
     * @param pjp
     * @return
     */
    @Around("controllerAspect()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable  {
        //开始时间
        long startTime = System.currentTimeMillis();
        Object result = null;
        SysLog sysLog = new SysLog();
        try {
            Object[] values = pjp.getArgs();
            String[] names = ((CodeSignature) pjp.getSignature()).getParameterNames();
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            Method method = methodSignature.getMethod();
            for (int i = 0; i < names.length; i++) {
                if (Arrays.stream(method.getParameterAnnotations()[i]).anyMatch(item -> item.annotationType().equals(RequestBody.class))) {
                    JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(values[i]));
                    for (String key : jsonObject.keySet()) {
                        if (key.equals("transId")) {
                            sysLog.setTransId(jsonObject.get(key).toString());
                            break;
                        }
                    }
                    sysLog.setReqData(jsonObject.toJSONString());
                }
            }
            // 执行目标方法
            result = pjp.proceed();
            if (result instanceof Result) {
                JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(((Result) result).getData()));
                sysLog.setResData(jsonObject.toJSONString());
            }
            sysLog.setCreateTime(new Date());
            sysLogMapper.insert(sysLog);
            //后置通知
            long endTime = System.currentTimeMillis();
            long excTime = (endTime - startTime);
            System.out.println("耗时：" + excTime);
            return result;
        } catch (Exception ex) {
            // 处理异常并返回自定义的错误信息
            throw ex;
        }


    }


}
