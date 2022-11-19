package com.xiaohu.cloud_notebook.controller;

import com.xiaohu.cloud_notebook.model.dto.AddNoteDto;
import com.xiaohu.cloud_notebook.service.NoteService;
import com.xiaohu.cloud_notebook_common.model.domain.User;
import com.xiaohu.cloud_notebook_common.result.Result;
import com.xiaohu.cloud_notebook_common.util.UserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiaohu
 * @date 2022/11/13/ 17:47
 * @description
 */
@Api(tags = "笔记信息服务")
@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @ApiOperation("伪用户登录")
    @GetMapping("/test")
    public Result<String> test(){
        User user = new User();
        user.setId(67L);
        user.setNackname("收割稻草的假面骑士");
        UserHolder.save(user);
        return Result.success("Hello world");
    }

    @ApiOperation("用户添加笔记")
    @PostMapping("/add")
    public Result<Long> addNote(@RequestBody AddNoteDto addNoteDto){
        Long noteId = noteService.addNote(addNoteDto);
        return Result.success(noteId);
    }
}
