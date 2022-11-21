package com.xiaohu.cloud_notebook.controller;

import com.xiaohu.cloud_notebook.model.dto.AddNoteDto;
import com.xiaohu.cloud_notebook.service.NoteService;
import com.xiaohu.cloud_notebook_common.model.domain.User;
import com.xiaohu.cloud_notebook_common.result.Result;
import com.xiaohu.cloud_notebook_common.util.UserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class NoteController {

    @Autowired
    private NoteService noteService;

    @ApiOperation("用户添加笔记")
    @PostMapping("/add")
    public Result<Long> addNote(@RequestBody AddNoteDto addNoteDto){
        Long noteId = noteService.addNote(addNoteDto);
        return Result.success(noteId);
    }
}
