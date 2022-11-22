package com.xiaohu.cloud_notebook_common.exception;

import com.xiaohu.cloud_notebook_common.result.Result;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xiaohu
 * @date 2022/11/08/ 21:10
 * @description
 */
@ControllerAdvice
@Component
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.fail();
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result error(BusinessException e) {
        e.printStackTrace();
        return Result.fail(e.getDescription());
    }
}
