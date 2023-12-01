package com.yyh.entity;

import java.util.List;

public interface Producer {
    public List<Expression> produceExpressions(Condition condition);

}
