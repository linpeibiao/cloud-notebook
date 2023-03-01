package com.xiaohu.cloud_notebook.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName t_note_content_history
 */
@TableName(value ="t_note_content_history")
@Data
public class NoteContentHistory implements Serializable {
    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 操作用户 id
     */
    private Long userId;

    /**
     * 笔记id
     */
    private Long noteId;

    /**
     * 笔记正文
     */
    private String content;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonIgnore
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    private Date updateTime;

    /**
     * 乐观锁
     */
    @Version
    @JsonIgnore
    @ApiModelProperty(value = "乐观锁")
    @TableField(value = "version", fill = FieldFill.INSERT)
    private Integer version;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}