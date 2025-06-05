package com.summer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "员工信息")
public class EmployeeDTO implements Serializable {

    @ApiModelProperty(value = "主键", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "用户名", example = "xiaozhi", required = true)
    private String username;

    @ApiModelProperty(value = "姓名", example = "小智", required = true)
    private String name;

    @ApiModelProperty(value = "手机号", example = "12345678901", required = true)
    private String phone;

    @ApiModelProperty(value = "性别", example = "1", required = true)
    private String sex;

    @ApiModelProperty(value = "身份证号", example = "123456789012345678", required = true)
    private String idNumber;
}
