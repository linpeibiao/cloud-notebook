package com.xiaohu.cloud_notebook_common.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName t_user
 * @author xiaohu
 */

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value ="t_user")
@Data
@NoArgsConstructor
public class User implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nackname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 用户状态 0-正常，1-禁用
     */
    private Integer status;

    /**
     * 用户角色
     */
    private Integer role;

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
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonIgnore
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 逻辑删除
     */
    @JsonIgnore
    @ApiModelProperty(value = "逻辑删除")
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleted;

    /**
     * 乐观锁
     */
    @JsonIgnore
    @ApiModelProperty(value = "乐观锁")
    @TableField(value = "version", fill = FieldFill.INSERT_UPDATE)
    @Version
    private Integer version;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}