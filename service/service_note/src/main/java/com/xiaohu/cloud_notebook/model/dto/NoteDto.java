package com.xiaohu.cloud_notebook.model.dto;

import com.xiaohu.cloud_notebook.constant.NoteEditingStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiaohu
 * @date 2022/11/18/ 21:54
 * @description
 */
@Data
public class NoteDto {
    /**
     * 标题
     */
    private String title;

    /**
     * 正文
     */
    private String content;

    /**
     * 所属知识库id
     */
    private Long noteBaseId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 编辑状态
     */
    private NoteEditingStatusEnum isEditing;

    /**
     * 备注
     */
    private String remark;
}
