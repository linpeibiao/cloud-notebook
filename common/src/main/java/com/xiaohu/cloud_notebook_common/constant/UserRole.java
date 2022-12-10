package com.xiaohu.cloud_notebook_common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xiaohu
 * @date 2022/12/10/ 22:35
 * @description 用户角色枚举类
 */
@Getter
@AllArgsConstructor
public enum UserRole {
    COMMON_USER(0, "普通用户"),
    ADMIN(1, "管理员");

    private int role;
    private String roleDescription;

}
