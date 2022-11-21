package com.xiaohu.cloud_notebook.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.*;

/**
 * @author xiaohu
 * @date 2022/11/21/ 19:48
 * @description
 */
@AllArgsConstructor
@Getter
public enum NoteEditingStatusEnum {
    NOTEDITING(0, "正常状态"),
    ISEDITING(1, "正在编辑");

    /**
     * 编辑状态码
     */
    @EnumValue
    private Integer statusCode;

    /**
     * 编辑状态描述
     */
    private String statusDescription;
}
