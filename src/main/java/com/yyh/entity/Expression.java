package com.yyh.entity;

import java.util.List;

public interface Expression<T,V,S> {
    public List<T> getListNumbers();
    public List<V> getListOperations();
    public S getAnswer();
    public void setListNumbers(List<T> listNumbers);

    public void setListOperations(List<T> listOperations);

    public void setAnswer(String answer);


}
