package com.xiaohu.cloud_notebook.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaohu.cloud_notebook.model.domain.NoteBase;
import com.xiaohu.cloud_notebook.model.dto.JoinNoteBaseDto;
import com.xiaohu.cloud_notebook.model.dto.NoteBaseDto;
import com.xiaohu.cloud_notebook.model.dto.NoteBaseInfoDto;
import com.xiaohu.cloud_notebook.service.NoteBaseService;
import com.xiaohu.cloud_notebook_common.model.domain.User;
import com.xiaohu.cloud_notebook_common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xiaohu
 * @date 2022/11/14/ 21:03
 * @description 知识库控制层接口
 */
@Api(tags = "知识库信息管理")
@RequestMapping("/base")
@RestController
public class NoteBaseController {

    @Autowired
    private NoteBaseService noteBaseService;

    @PostMapping("/add")
    @ApiOperation("用户添加知识库")
    public Result<Long> addNoteBase(@RequestBody NoteBaseDto noteBaseDto){
        Long noteBaseId = noteBaseService.addNoteBase(noteBaseDto);
        return Result.success(noteBaseId);
    }

    @ApiOperation("用户修改知识库基本信息")
    @PutMapping("/info-update")
    public Result<String> infoUpdate(@RequestBody NoteBaseInfoDto noteBaseInfoDto){
        boolean isSuccess = noteBaseService.infoUpdate(noteBaseInfoDto);
        return isSuccess ? Result.success("修改成功") : Result.fail();
    }

    /**
     * 转让知识库
     * @return
     */
    @ApiOperation("用户转让知识库")
    @PutMapping("/user-transfer/{noteBaseId}")
    public Result transferNoteBase(@RequestBody NoteBaseDto noteBaseDto, @PathVariable("noteBaseId") Long noteBaseId){
        boolean isSuccess = noteBaseService.transferTo(noteBaseDto, noteBaseId);
        return isSuccess ? Result.success("修改成功") : Result.fail("");
    }

    /**
     * 加入知识库
     * @param joinNoteBaseDto
     * @return
     */
    @ApiOperation("用户加入知识库")
    @PostMapping("/user-join")
    public Result joinNoteBase(@RequestBody JoinNoteBaseDto joinNoteBaseDto){
        boolean isSuccess = noteBaseService.joinNoteBase(joinNoteBaseDto);
        return isSuccess ? Result.success("加入成功") : Result.fail("");
    }

    /**
     * 退出知识库
     * @param noteBaseId
     * @return
     */
    @ApiOperation("用户退出知识库")
    @PutMapping("/user-exit/{noteBaseId}")
    public Result exitNoteBase(@PathVariable("noteBaseId") Long noteBaseId){
        boolean isSuccess = noteBaseService.exitNoteBase(noteBaseId);
        return isSuccess ? Result.success("退出成功") : Result.fail("");
    }

    @ApiOperation("通过id获取知识库信息")
    @GetMapping("/get-by-id/{noteBaseId}")
    public Result<NoteBase> getNoteBaseById(@PathVariable("noteBaseId") Long noteBaseId){
        NoteBase noteBase = noteBaseService.getNoteBaseById(noteBaseId);
        return Result.success(noteBase);
    }

    @ApiOperation("分页获取用户知识库")
    @GetMapping("/page-by-userId/{pageNum}/{pageSize}/{userId}")
    public Result<IPage<NoteBase>> getNoteBasePageByUserId(@PathVariable("pageNum")int pageNum,
                                                          @PathVariable("pageSize")int pageSize,
                                                          @PathVariable("userId") Long userId){
        if (pageNum <= 0 || pageSize <= 0){
            return Result.fail("分页参数错误");
        }
        IPage<NoteBase> page = noteBaseService.getNoteBasePageByUserId(pageNum, pageSize, userId);
        return Result.success(page);
    }

    @ApiOperation("分页模糊查询知识库")
    @GetMapping("/page-by-name/{pageNum}/{pageSize}/{noteBaseName}")
    public Result<IPage<NoteBase>> getNoteBasePageByNoteBaseName(@PathVariable("pageNum")int pageNum,
                                                           @PathVariable("pageSize")int pageSize,
                                                           @PathVariable("noteBaseName") String noteBaseName){
        if (pageNum <= 0 || pageSize <= 0){
            return Result.fail("分页参数错误");
        }
        IPage<NoteBase> page = noteBaseService.getNoteBasePageByNoteBaseName(pageNum, pageSize, noteBaseName);
        return Result.success(page);
    }

    @ApiOperation("获取知识库所有用户")
    @GetMapping("/get-users/{noteBaseId}")
    public Result<List<User>> getUsersOfNoteBase(@PathVariable("noteBaseId") Long noteBaseId){
        List<User> userList = noteBaseService.getUsersOfNoteBase(noteBaseId);
        return Result.success(userList);
    }
}
