package com.yyh.POJO;

import com.yyh.POJO.Condition;
import com.yyh.POJO.Expression;

import java.util.List;

public interface Producer {
    public List<Expression> produceSimpleExpression(Condition condition);

}
