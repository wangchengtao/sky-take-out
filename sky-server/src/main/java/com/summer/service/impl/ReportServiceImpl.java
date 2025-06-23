package com.summer.service.impl;

import com.summer.entity.Orders;
import com.summer.mapper.OrderMapper;
import com.summer.service.ReportService;
import com.summer.vo.TurnoverReportVO;
import com.summer.vo.UserReportVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public TurnoverReportVO getTurnover(LocalDate begin, LocalDate end) {
        ArrayList<LocalDate> localDates = new ArrayList<>();

        do {
            localDates.add(begin);
            begin = begin.plusDays(1);
        } while (!begin.equals(end));

        ArrayList<Double> doubles = new ArrayList<>();

        for (LocalDate date : localDates) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            HashMap<String, Object> map = new HashMap<>();
            map.put("status", Orders.COMPLETED);
            map.put("begin", beginTime);
            map.put("end", endTime);

            Double turnover = orderMapper.sumByMap(map);
            doubles.add(turnover == null ? 0.0 : turnover);
        }

        return TurnoverReportVO.builder()
                .dateList(localDates.stream().map(LocalDate::toString).collect(Collectors.joining(",")))
                .turnoverList(doubles.stream().map(String::valueOf).collect(Collectors.joining(",")))
                .build();
    }

    @Override
    public UserReportVO getUser(LocalDate begin, LocalDate end) {
        List<LocalDate> localDates = new ArrayList<>();

        do {
            localDates.add(begin);
            begin = begin.plusDays(1);
        } while (!begin.equals(end));

        List<Integer> newUsers = new ArrayList<>();
        List<Integer> totalUsers = new ArrayList<>();

        for (LocalDate date : localDates) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Integer newUser = getUserCount(beginTime, endTime);
            Integer totalUser = getUserCount(null, endTime);

            newUsers.add(newUser);
            totalUsers.add(totalUser);
        }

        return UserReportVO.builder()
                .dateList(localDates.stream().map(LocalDate::toString).collect(Collectors.joining(",")))
                .newUserList(newUsers.stream().map(String::valueOf).collect(Collectors.joining(",")))
                .totalUserList(totalUsers.stream().map(String::valueOf).collect(Collectors.joining(",")))
                .build();
    }

    private Integer getUserCount(LocalDateTime beginTime, LocalDateTime endTime) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("begin", beginTime);
        map.put("end", endTime);

        return orderMapper.countByMap(map);
    }
}
