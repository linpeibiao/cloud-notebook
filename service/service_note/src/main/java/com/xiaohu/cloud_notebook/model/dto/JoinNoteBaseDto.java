package com.xiaohu.cloud_notebook.model.dto;

import lombok.Data;

/**
 * @author xiaohu
 * @date 2022/11/16/ 23:12
 * @description 加入知识库参数体
 */
@Data
public class JoinNoteBaseDto {
    private Long noteBaseId;
    private String password;
}
