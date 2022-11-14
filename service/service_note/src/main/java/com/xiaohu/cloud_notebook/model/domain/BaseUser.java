package com.xiaohu.cloud_notebook.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户---知识库关系表
 * @TableName t_base_user
 */
@TableName(value ="t_base_user")
@Data
public class BaseUser implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 知识库id
     */
    private Long noteBaseId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 备用字段1
     */
    private String backup1;

    /**
     * 备用字段2
     */
    private String backup2;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 乐观锁
     */
    private Integer version;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}