package top.blackcat.sm.secret.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title: 写注释，下班早
 * @Desc: 日志注解
 * @Author: Jelly
 * @Date: 2023/11/24
 * @Version V1.0
 */
@Target({ ElementType.METHOD})
//定义该注解的生命周期
//(始终不会被丢弃运行期也保留)
@Retention(RetentionPolicy.RUNTIME)
//表示是否将注解信息添加在java文档中
public @interface RecordClientRequest {

}