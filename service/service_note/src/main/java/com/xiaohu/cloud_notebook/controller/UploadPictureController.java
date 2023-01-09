package com.xiaohu.cloud_notebook.controller;

import com.xiaohu.cloud_notebook.service.TencentCosService;
import com.xiaohu.cloud_notebook_common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiaohu
 * @date 2023/01/08/ 15:18
 * @description 图片上传
 */
@RestController
@RequestMapping("/picture")
@Api(tags = "图片接口")
public class UploadPictureController {

    @Autowired
    private TencentCosService tencentCosService;
    @ApiOperation("上传图片")
    @PostMapping(value = "/upload", consumes = "multipart/*", headers="content-type=multipart/form-data")
    public Result uploadPicture(@ApiParam(value = "上传的文件" ,required = true) MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response){
        String imageUrl = tencentCosService.ContentCOS(multipartFile, request, response);
        if (imageUrl == null) {
            return Result.fail("上传失败");
        }
        return Result.success(imageUrl);
    }
}
