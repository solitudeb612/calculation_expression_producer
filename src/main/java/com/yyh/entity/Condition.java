package com.yyh.entity;

import com.yyh.utils.StaticVariableGetter;

import java.util.List;

public class Condition {
    //产生expression的数量（要产生多少道题）
    private int generate_expression_number = 5;
    //expression中操作数的数量（要产生几式运算题）
    private int expression_number = 2;
    //expression中操作数的大小（多少以内的运算题）
    private int range = 100;
    //运算符类型（要产生什么类型的运算题）
    private List<String> operation = StaticVariableGetter.getAllConstants(Operation.class);

    public Condition() {
    }
    public Condition(int generate_expression_number, int expression_number) {
        this.generate_expression_number = generate_expression_number;
        this.expression_number = expression_number;
    }


    @Override
    public String toString() {
        return "Condition{" +
                "generate_expression_number=" + generate_expression_number +
                ", expression_number=" + expression_number +
                ", range=" + range +
                ", operation=" + operation +
                '}';
    }

    public int getGenerate_expression_number() {
        return generate_expression_number;
    }

    public void setGenerate_expression_number(int generate_expression_number) {
        this.generate_expression_number = generate_expression_number;
    }

    public int getExpression_number() {
        return expression_number;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public List<String> getOperation() {
        return operation;
    }

    public void setOperation(List<String> operation) {
        this.operation = operation;
    }

    public void setExpression_number(int expression_number) {
        this.expression_number = expression_number;
    }

}
