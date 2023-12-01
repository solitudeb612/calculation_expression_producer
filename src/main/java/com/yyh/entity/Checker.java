package com.yyh.entity;

import java.util.HashMap;
import java.util.Map;

public class Checker {
    private static Map<Expression, Object> map = new HashMap<>();

    public static Map<Expression, Object> getMap() {
        return map;
    }

    public static void setMap(Map<Expression, Object> map) {
        Checker.map = map;
    }

    public static boolean checkRepeat(Expression expression) {
        return map.containsKey(expression);
    }
}
