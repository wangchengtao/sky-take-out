package com.summer.controller.admin;

import com.summer.dto.EmployeeDTO;
import com.summer.dto.EmployeePageQueryDTO;
import com.summer.entity.Employee;
import com.summer.result.PageResult;
import com.summer.result.Result;
import com.summer.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工管理")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @ApiOperation("新增员工")
    @PostMapping
    public Result<Object> save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工: {}", employeeDTO);

        employeeService.save(employeeDTO);

        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("员工列表")
    public Result<PageResult<Employee>> page(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("员工分页查询: {}", employeePageQueryDTO);

        PageResult<Employee> pageResult = employeeService.pageQuery(employeePageQueryDTO);


        return Result.success(pageResult);
    }

    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用员工账号")
    public Result<Object> startOrStop(@PathVariable Integer status, Long id) {

        log.info("启用禁用员工账号：{}, {}", id, status);

        employeeService.startOrStop(status, id);

        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("员工详情")
    public Result<Employee> getById(@PathVariable("id") Long id) {

        Employee employee = employeeService.getById(id);

        return Result.success(employee);
    }

    @PutMapping
    @ApiOperation("编辑")
    public Result<Object> update(@RequestBody EmployeeDTO employeeDTO) {

        log.info("编辑员工信息：{}", employeeDTO);

        employeeService.update(employeeDTO);

        return Result.success();
    }
}
