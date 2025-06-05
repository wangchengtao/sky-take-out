package com.summer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("员工分页查询对象")
public class EmployeePageQueryDTO implements Serializable {

    @ApiModelProperty(value = "员工姓名", example = "张三")
    private String name;

    @ApiModelProperty(value = "当前页码", example = "1")
    private Integer page;

    @ApiModelProperty(value = "每页显示条数", example = "10")
    private Integer pageSize;
}
