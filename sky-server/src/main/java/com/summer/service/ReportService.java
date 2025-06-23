package com.summer.service;

import com.summer.vo.TurnoverReportVO;
import com.summer.vo.UserReportVO;

import java.time.LocalDate;

public interface ReportService {
    TurnoverReportVO getTurnover(LocalDate begin, LocalDate end);

    UserReportVO getUser(LocalDate begin, LocalDate end);
}
