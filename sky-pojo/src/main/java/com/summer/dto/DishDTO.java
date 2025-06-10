package com.summer.dto;

import com.summer.entity.DishFlavor;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDTO implements Serializable {

    private Long id;

    @ApiModelProperty(value = "菜品名称", example = "隆江猪脚饭", required = true)
    private String name;

    @ApiModelProperty(value = "菜品分类id", example = "1", required = true)
    private Long categoryId;

    @ApiModelProperty(value = "菜品价格", example = "10.00", required = true)
    private BigDecimal price;

    @ApiModelProperty(value = "菜品图片", example = "https://www.baidu.com/img/bd_logo1.png", required = true)
    private String image;

    @ApiModelProperty(value = "菜品描述", example = "隆江猪脚饭，入口鲜嫩，外皮酥软")
    private String description;

    @ApiModelProperty(value = "菜品状态 0 停售 1 起售", example = "1", required = true, allowableValues = "0,1")
    private Integer status;

    @ApiModelProperty(value = "菜品的口味")
    private List<DishFlavor> flavors = new ArrayList<>();

}
