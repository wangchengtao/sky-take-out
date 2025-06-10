package com.summer.entity;

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
public class DishFlavor implements Serializable {
    private static final long serialVersionUID = -902034313273613972L;

    private Long id;

    @ApiModelProperty(value = "菜品id", example = "1")
    private Long dishId;

    @ApiModelProperty(value = "口味名称", example = "辣度", required = true)
    private String name;

    @ApiModelProperty(value = "口味数据list", example = "['辣','不辣']", required = true)
    private String value;
}
