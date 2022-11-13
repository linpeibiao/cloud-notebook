package com.xiaohu.cloud_notebook.controller;

import com.xiaohu.cloud_notebook_common.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaohu
 * @date 2022/11/13/ 17:47
 * @description
 */
@RestController
@RequestMapping("/note")
public class NoteController {

    @GetMapping("/test")
    public Result<String> test(){
        return Result.success("Hello world");
    }
}
