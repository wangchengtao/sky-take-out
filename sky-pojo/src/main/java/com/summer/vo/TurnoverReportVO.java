package com.summer.vo;

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
public class TurnoverReportVO implements Serializable {
    private static final long serialVersionUID = 234067473015875761L;

    @ApiModelProperty(value = "日期, 以逗号分割", example = "2025-01-01,2025-01-02,2025-01-03")
    private String dateList;

    @ApiModelProperty(value = "营业额, 以逗号分割", example = "100.00,200.00,300.00")
    private String turnoverList;
}
