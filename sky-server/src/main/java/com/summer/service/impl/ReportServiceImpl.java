package com.summer.service.impl;

import com.summer.dto.GoodsSalesDTO;
import com.summer.entity.Orders;
import com.summer.mapper.OrderMapper;
import com.summer.service.ReportService;
import com.summer.vo.OrderReportVO;
import com.summer.vo.SalesTop10ReportVO;
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

    @Override
    public OrderReportVO getOrder(LocalDate begin, LocalDate end) {
        ArrayList<LocalDate> localDates = new ArrayList<>();

        do {
            localDates.add(begin);
            begin = begin.plusDays(1);
        } while (!begin.equals(end));

        ArrayList<Integer> orderCountList = new ArrayList<>();
        ArrayList<Integer> validOrderCountList = new ArrayList<>();

        for (LocalDate date : localDates) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Integer orderCount = getOrderCount(beginTime, endTime, null);
            Integer validOrderCount = getOrderCount(beginTime, endTime, Orders.COMPLETED);

            orderCountList.add(orderCount);
            validOrderCountList.add(validOrderCount);
        }

        Integer totalOrderCount = orderCountList.stream().reduce(Integer::sum).get();
        Integer validOrderCount = validOrderCountList.stream().reduce(Integer::sum).get();

        Double rate = 0.0;
        if (totalOrderCount != 0) {
            rate = validOrderCount.doubleValue() / totalOrderCount;
        }

        return OrderReportVO.builder()
                .dateList(localDates.stream().map(LocalDate::toString).collect(Collectors.joining(",")))
                .orderCountList(orderCountList.stream().map(String::valueOf).collect(Collectors.joining(",")))
                .validOrderCountList(validOrderCountList.stream().map(String::valueOf).collect(Collectors.joining(",")))
                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(rate)
                .build();

    }

    @Override
    public SalesTop10ReportVO getTop10(LocalDate begin, LocalDate end) {
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

        List<GoodsSalesDTO> goodsSalesDTOList = orderMapper.getTop10(beginTime, endTime);

        String nameList = goodsSalesDTOList.stream().map(GoodsSalesDTO::getName).collect(Collectors.joining(","));
        String numberList = goodsSalesDTOList.stream().map(GoodsSalesDTO::getNumber).map(String::valueOf).collect(Collectors.joining(","));

        return SalesTop10ReportVO.builder()
                .nameList(nameList)
                .numberList(numberList)
                .build();
    }

    private Integer getOrderCount(LocalDateTime beginTime, LocalDateTime endTime, Integer status) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("begin", beginTime);
        map.put("end", endTime);

        return orderMapper.countByMap(map);
    }

    private Integer getUserCount(LocalDateTime beginTime, LocalDateTime endTime) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("begin", beginTime);
        map.put("end", endTime);

        return orderMapper.countByMap(map);
    }
}
