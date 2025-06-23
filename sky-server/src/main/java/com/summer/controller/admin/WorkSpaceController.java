package com.summer.controller.admin;

import com.summer.result.Result;
import com.summer.service.WorkSpaceService;
import com.summer.vo.BusinessDataVO;
import com.summer.vo.DishOverViewVO;
import com.summer.vo.OrderOverViewVO;
import com.summer.vo.SetmealOverViewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("admin/workspace")
@Api(tags = "工作台")
@Slf4j
public class WorkSpaceController {

    @Autowired
    private WorkSpaceService workSpaceService;

    @GetMapping("businessData")
    @ApiOperation("今日营业数据")
    public Result<BusinessDataVO> businessData() {
        LocalDateTime begin = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime end = LocalDateTime.now().with(LocalTime.MAX);

        BusinessDataVO vo = workSpaceService.getBusinessData(begin, end);

        return Result.success(vo);
    }

    @GetMapping("overviewOrders")
    @ApiOperation("订单统计")
    public Result<OrderOverViewVO> orderOverView() {
        return Result.success(workSpaceService.getOrderOverView());
    }

    @GetMapping("overviewDishes")
    @ApiOperation("菜品统计")
    public Result<DishOverViewVO> dishOverView() {
        return Result.success(workSpaceService.getDishOverView());
    }

    @GetMapping("overviewSetmeals")
    @ApiOperation("套餐统计")
    public Result<SetmealOverViewVO> setmealOverView() {
        return Result.success(workSpaceService.getSetmealOverView());
    }
}
