package com.xiaohu.cloud_notebook.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 笔记文档表
 * @TableName t_note
 */
@TableName(value ="t_note")
@Data
public class Note implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 创建者id
     */
    private Long userId;

    /**
     * 创建者昵称
     */
    private String userNackname;

    /**
     * 正文
     */
    private String content;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 备用字段1
     */
    private String backup1;

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
    private Integer isDeleted;

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
    @JsonIgnore
    @ApiModelProperty(value = "乐观锁")
    @TableField(value = "version", fill = FieldFill.INSERT)
    @Version
    private Integer version;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}