package com.summer.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReportVO implements Serializable {
    private static final long serialVersionUID = -1027470239880860523L;

    private String dateList;

    private String totalUserList;

    private String newUserList;
}
