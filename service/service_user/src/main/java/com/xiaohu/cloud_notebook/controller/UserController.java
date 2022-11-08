package com.xiaohu.cloud_notebook.controller;

import com.xiaohu.cloud_notebook_common.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaohu
 * @date 2022/11/09/ 0:12
 * @description
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     *
     * @return
     */
    @GetMapping("/test")
    public Result<String> test(){
        return Result.success("hello world");
    }
}
