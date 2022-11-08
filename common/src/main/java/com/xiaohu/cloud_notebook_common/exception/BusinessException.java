package com.xiaohu.cloud_notebook_common.exception;

import com.xiaohu.cloud_notebook_common.result.ResultCode;

/**
 * @author xiaohu
 * @date 2022/11/08/ 21:11
 * @description
 */
public class BusinessException extends RuntimeException{
    private final int code;

    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
        this.description = resultCode.getDescription();
    }

    public BusinessException(ResultCode resultCode, String description) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
