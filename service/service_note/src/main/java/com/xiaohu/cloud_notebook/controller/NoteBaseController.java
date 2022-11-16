package com.xiaohu.cloud_notebook.controller;

import com.xiaohu.cloud_notebook.model.dto.NoteBaseDto;
import com.xiaohu.cloud_notebook.model.dto.NoteBaseInfoDto;
import com.xiaohu.cloud_notebook.service.NoteBaseService;
import com.xiaohu.cloud_notebook_common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/info/update")
    public Result<String> infoUpdate(@RequestBody NoteBaseInfoDto noteBaseInfoDto){
        boolean isSuccess = noteBaseService.infoUpdate(noteBaseInfoDto);
        return isSuccess ? Result.success("修改成功") : Result.fail();
    }

    /**
     * 转让知识库
     * @return
     */
    @ApiOperation("用户转让知识库")
    @PostMapping("/user/transfer/{noteBaseId}")
    public Result transferNoteBase(@RequestBody NoteBaseDto noteBaseDto, @PathVariable("noteBaseId") Long noteBaseId){
        boolean isSuccess = noteBaseService.transferTo(noteBaseDto, noteBaseId);
        return isSuccess ? Result.success("修改成功") : Result.fail("");
    }

}
