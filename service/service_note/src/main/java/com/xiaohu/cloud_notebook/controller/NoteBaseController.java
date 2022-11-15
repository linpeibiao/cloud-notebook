package com.xiaohu.cloud_notebook.controller;

import com.xiaohu.cloud_notebook.model.dto.NoteBaseDto;
import com.xiaohu.cloud_notebook.service.NoteBaseService;
import com.xiaohu.cloud_notebook_common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/add/note/base")
    @ApiOperation("用户添加知识库")
    public Result<Long> addNoteBase(@RequestBody NoteBaseDto noteBaseDto){
        Long noteBaseId = noteBaseService.addNoteBase(noteBaseDto);
        return Result.success(noteBaseId);
    }

}
