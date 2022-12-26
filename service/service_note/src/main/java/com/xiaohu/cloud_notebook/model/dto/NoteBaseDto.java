package com.xiaohu.cloud_notebook.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaohu
 * @date 2022/11/15/ 22:39
 * @description 知识库数据传输实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteBaseDto {
    private String noteBaseName;
    /**
     * 创建用户id
     */
    private Long userId;
    private String userNackName;
    private String brief;
    private Integer status;
    private String password;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}
