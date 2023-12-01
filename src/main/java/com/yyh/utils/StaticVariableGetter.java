package com.yyh.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class StaticVariableGetter {
    public static List<String> getAllConstants(Class<?> clazz) {
        List<String> constantValues = new ArrayList<>();
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers()) && java.lang.reflect.Modifier.isFinal(field.getModifiers())) {
                try {
                    constantValues.add((String) field.get(null));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return constantValues;
    }


}
