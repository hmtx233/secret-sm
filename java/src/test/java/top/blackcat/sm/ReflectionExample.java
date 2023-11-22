package top.blackcat.sm;

import java.lang.reflect.Method;

public class ReflectionExample {
    public static void main(String[] args) {
        try {
            // 获取目标类的Class对象
            Class<?> targetClass = MyClass.class;

            // 获取目标字段的setter方法
            Method setterMethod = targetClass.getMethod("setMyField", String.class);

            // 创建目标类的实例
            MyClass myObject1 = new MyClass();
            MyClass myObject2 = new MyClass();

            // 获取原来的值
            String originalValue1 = myObject1.getMyField();
            String originalValue2 = myObject2.getMyField();

            // 输出原来的值
            System.out.println("原来的值1：" + originalValue1);
            System.out.println("原来的值2：" + originalValue2);

            // 设置新的值
            setterMethod.invoke(myObject1, "新的值1");
            setterMethod.invoke(myObject2, "新的值2");

            // 获取更新后的值
            String updatedValue1 = myObject1.getMyField();
            String updatedValue2 = myObject2.getMyField();

            // 输出更新后的值
            System.out.println("更新后的值1：" + updatedValue1);
            System.out.println("更新后的值2：" + updatedValue2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class MyClass {
    private String myField;

    public String getMyField() {
        return myField;
    }

    public void setMyField(String myField) {
        this.myField = myField;
    }
}