package com.yyh.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //生成get set方法
@NoArgsConstructor //生成无参的构造方法
@AllArgsConstructor //生成该类下全部属性的构造方法,可以代替@Autowired注入，即类中的这些属性不用写@Autowired，就能注入
public class Result<T>{
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success() {
        return new Result<>(20000, "success", null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(20000, "success", data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(20000, message, data);
    }

    public static <T> Result<T> success(String message) {
        return new Result<>(20000, message, null);
    }



    public static <T> Result<T> fail() {
        return new Result<>(20001, "error", null);
    }

    public static <T> Result<T> fail(String message) {
        return new Result<>(20001, message, null);
    }

    public static <T> Result<T> fail(T data) {
        return new Result<>(20001, "error", data);
    }

    public static <T> Result<T> fail(String message, T data) {
        return new Result<>(20001, message, data);
    }

    public static <T> Result<T> fail(Integer code, String message, T data) {
        return new Result<>(code, message, data);
    }

    public static <T> Result<T> fail(Integer code, String message) {
        return new Result<>(code, message, null);
    }

}
