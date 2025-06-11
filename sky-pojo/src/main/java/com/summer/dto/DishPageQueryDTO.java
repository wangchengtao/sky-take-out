package com.summer.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DishPageQueryDTO implements Serializable {

    @ApiModelProperty(value = "页码", example = "1")
    private int page = 1;

    @ApiModelProperty(value = "每页条数", example = "10")
    private int pageSize = 15;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "分类id")
    private Integer categoryId;

    @ApiModelProperty(value = "状态 0 停售 1 起售", example = "1", allowableValues = "0,1")
    private Integer status;
}
