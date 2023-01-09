package com.xiaohu.cloud_notebook.service;

import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiaohu
 * @date 2022/09/03/ 22:31
 * @description 腾讯图床上传图片业务接口
 */
public interface TencentCosService {

    /**
     * 获得上传到图床的图片链接
     * @param fileDataFileName
     * @param request
     * @param response
     * @return
     */
    String ContentCOS(MultipartFile fileDataFileName,
                                          HttpServletRequest request,
                                          HttpServletResponse response);
}