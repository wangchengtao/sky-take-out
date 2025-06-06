package com.summer.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CategoryPageQueryDTO {

    @ApiModelProperty(value = "类型", allowableValues = "1, 2")
    private Integer type;

    @ApiModelProperty(value = "页码", example = "1")
    private Integer page;

    @ApiModelProperty(value = "页大小", example = "10")
    private Integer pageSize;
}
