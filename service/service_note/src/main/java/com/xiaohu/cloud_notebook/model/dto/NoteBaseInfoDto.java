package com.xiaohu.cloud_notebook.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiaohu
 * @date 2022/11/16/ 20:59
 * @description
 */
@Data
public class NoteBaseInfoDto {
    private Long id;
    private String noteBaseName;
    private String brief;
    private Integer status;
    private String password;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
}
