package com.xiaohu.cloud_notebook.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author xiaohu
 * @date 2022/11/18/ 21:54
 * @description
 */
@Data
public class AddNoteDto {
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
     * 备注
     */
    private String remark;
}
