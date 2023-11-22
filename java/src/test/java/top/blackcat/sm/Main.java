package top.blackcat.sm;

import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import top.blackcat.sm.secret.User;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        User user = new User();
        user.setRealName("海波");
        user.setPhone("18701559738");

        System.out.println(user);
        get(user);
        System.out.println(user);


    }


    public static void get(Object object) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        // 获取类的所有属性
        Field[] fields = object.getClass().getDeclaredFields();

        // 遍历属性并打印属性名
        for (Field field : fields) {
            String fieldName = field.getName();
            System.out.println(fieldName);
        }

        Method setterMethod = object.getClass().getMethod("setPhone", String.class);
        setterMethod.invoke(object,"新");


    }

}