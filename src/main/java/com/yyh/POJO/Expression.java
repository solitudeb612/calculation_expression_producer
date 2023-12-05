package com.yyh.POJO;

import java.util.List;

public interface Expression<T,V,S> {
    public List<T> getListNumbers();
    public List<V> getListOperations();
    public S getAnswer();

    public Object getCombinedExpression();
    public void setListNumbers(List<T> listNumbers);

    public void setListOperations(List<T> listOperations);

    public void setAnswer(String answer);
    public void setCombinedExpression(String CombinedExpression);


}
