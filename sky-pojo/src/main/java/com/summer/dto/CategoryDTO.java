package com.summer.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
// @ApiModel(description = "分类信息")
public class CategoryDTO implements Serializable {

    private Long id;

    @ApiModelProperty(value = "分类名称", example = "菜品分类", required = true)
    private String name;

    @ApiModelProperty(value = "顺序", example = "1", required = true)
    private Integer sort;

    @ApiModelProperty(value = "类型 1 菜品分类 2 套餐分类", example = "1", required = true)
    private Integer type;
}
