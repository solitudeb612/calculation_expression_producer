package com.yyh.POJO;

import java.math.BigDecimal;
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

    /**
     * 查重
     * @param expression
     * @return
     */
    public static boolean checkRepeat(Expression expression) {
        return map.containsKey(expression);
    }

    public static boolean checkResultLeftRange(BigDecimal left_range, BigDecimal result) {
        // compareTo 返回值<0表示result小于left_range；返回值=0表示两者相等；返回值>0表示result大于left_range
        return result.compareTo(left_range) >= 0;
    }

    public static boolean checkResultRightRange(BigDecimal right_range, BigDecimal result) {
        return result.compareTo(right_range) <= 0;
    }

    /**
     * 检查结果是否有小数
     * @param result
     * @return
     */
    public static boolean checkResultPrecision(BigDecimal result) {
        // 去除尾部的0
        BigDecimal stripped = result.stripTrailingZeros();
        // 如果scale小于或等于0，则说明没有小数部分
        return stripped.scale() > 0;
    }
}
