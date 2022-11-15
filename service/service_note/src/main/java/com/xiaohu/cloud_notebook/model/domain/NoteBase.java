package com.xiaohu.cloud_notebook.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 知识库表
 * @TableName t_note_base
 */
@TableName(value ="t_note_base")
@Data
public class NoteBase implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 知识库名
     */
    private String noteBaseName;

    /**
     * 创建者id
     */
    private Long userId;

    /**
     * 创建者昵称
     */
    private String userNackname;

    /**
     * 简介
     */
    private String brief;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 密码
     */
    private String password;

    /**
     * 备用字段2
     */
    private String backup2;

    /**
     * 备注
     */
    private String remark;

    /**
     * 逻辑删除
     */
    @JsonIgnore
    @ApiModelProperty(value = "逻辑删除")
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleted;

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