package javawebinar.basejava;

import javawebinar.basejava.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Resume r = new Resume();
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");
        // TODO : invoke r.toString via reflection
        System.out.println(r);

        Method method = r.getClass().getMethod("toString");
        System.out.println(method.invoke(r));

        Method[] methods = r.getClass().getDeclaredMethods();

        for (Method m : methods) {
            if (m.getName().equals("toString")) {
                System.out.println(m.invoke(r));
                break;
            }
        }

    }
}