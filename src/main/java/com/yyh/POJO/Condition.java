package com.yyh.POJO;

import com.yyh.constants.Operation;
import com.yyh.utils.StaticVariableGetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Condition implements Serializable {
    //产生expression的数量（要产生多少道题）
    private int generate_expression_number = 5;
    //expression中操作数的数量（要产生几式运算题）
    private int expression_number = 2;
    //expression中操作数的大小（多少以内的运算题）
    private int range = 100;
    //运算符类型（要产生什么类型的运算题）
    private List<String> operation = StaticVariableGetter.getAllConstants(Operation.class);
    //精确度（保留多少位小数）
    private int precision = 3;

}
