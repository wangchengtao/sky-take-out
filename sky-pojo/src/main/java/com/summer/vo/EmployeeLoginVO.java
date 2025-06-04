package com.summer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "员工登录返回的数据格式")
public class EmployeeLoginVO implements Serializable {

    private static final long serialVersionUID = -7327003477090097755L;

    @ApiModelProperty(value = "员工id", example = "1", required = true)
    private Long id;

    @ApiModelProperty(value = "用户名", example = "admin", required = true)
    private String userName;

    @ApiModelProperty(value = "员工姓名", example = "Summer", required = true)
    private String name;

    @ApiModelProperty(value = "jwt令牌", example = "234sdf7sdfskdfhk23g4sd9f6sd98f", required = true)
    private String token;
}
