package com.xiaohu.cloud_notebook_common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    /**
     * 构造response，code用于前端判断，message可以在前端显示错误信息，视情况传data
     */
    private int code;
    private String message;
    private T data;
    // 20000
    public static <T> Result success(T data) {
        return new Result(ResultCode.SUCCESS.getCode(), "请求成功", data);
    }
    // 失败
    public static Result fail() {
        return new Result(ResultCode.FAIL.getCode(), "请求失败", null);
    }
    public static Result fail(String message) {
        return new Result(40000, message, null);
    }

    // 失败
    public static Result fail(ResultCode resultCode) {
        return new Result(resultCode.getCode(), resultCode.getMessage(), null);
    }

    protected static <T> Result<T> build(T data) {
        Result<T> result = new Result<T>();
        if (data != null) {
            result.setData(data);
        }
        return result;
    }
    public static <T> Result<T> build(T body, ResultCode resultCode) {
        Result<T> result = build(body);
        result.setCode(resultCode.getCode());
        result.setMessage(resultCode.getMessage());
        return result;
    }
}
