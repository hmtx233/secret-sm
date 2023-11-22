package top.blackcat.sm.secret.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jelly
 * @create 2023/11/20 17:01
 * @desc 自定义注解
 **/
//表示该注解用于什么地方。默认值为任何元素，表示该注解用于什么地方,参数和方法
//(包 方法)
@Target({ElementType.PACKAGE, ElementType.METHOD})
//定义该注解的生命周期
//(始终不会被丢弃运行期也保留)
@Retention(RetentionPolicy.RUNTIME)
//表示是否将注解信息添加在java文档中
public @interface SecretEncrypt {

    //  是否忽略 加密
    boolean ignoreEncrypt() default false;

    //  默认是解密  // 0 加解密 1 加密  2 解密
    int type() default 2;
    // 自己添加
    String[] optField() default {};


}