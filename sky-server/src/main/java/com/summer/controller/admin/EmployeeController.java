package com.summer.controller.admin;

import com.summer.dto.EmployeeDTO;
import com.summer.result.Result;
import com.summer.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
}
