package com.xiaohu.cloud_notebook.controller;

import com.xiaohu.cloud_notebook.model.domain.Note;
import com.xiaohu.cloud_notebook.model.dto.NoteDto;
import com.xiaohu.cloud_notebook.service.NoteService;
import com.xiaohu.cloud_notebook_common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @ApiOperation("getToken测试")
    @GetMapping("/getToken")
    public String getToken(HttpServletRequest request){
        return request.getHeader("token");
    }

    @ApiOperation("用户添加笔记")
    @PostMapping("/add")
    public Result<Long> addNote(@RequestBody NoteDto noteDto){
        Long noteId = noteService.addNote(noteDto);
        return Result.success(noteId);
    }

    @PostMapping("/delete/{noteId}")
    @ApiOperation("用户删除笔记")
    public Result deleteNote(@PathVariable("noteId") Long noteId){
        boolean isSuccess = noteService.deleteNote(noteId);
        return isSuccess ? Result.success("删除成功") : Result.fail();
    }

    @ApiOperation("用户编辑笔记")
    @PostMapping("/edit/{noteId}")
    public Result editNote(@RequestBody NoteDto noteDto, @PathVariable("noteId") Long noteId){
        boolean isSuccess = noteService.editNode(noteDto, noteId);
        return isSuccess ? Result.success("保存成功") : Result.fail("try again");
    }

    @ApiOperation("获取用户某知识库笔记")
    @PostMapping("/list/by/noteBaseId/{noteBaseId}")
    public Result<List<Note>> getNotesByNoteBaseId(@PathVariable("noteBaseId") Long noteBaseId){
        List<Note> noteList = noteService.getNotesByNoteBaseId(noteBaseId);
        return Result.success(noteList);
    }

}
