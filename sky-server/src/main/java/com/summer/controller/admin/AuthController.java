package com.summer.controller.admin;

import com.summer.constant.JwtClaimsConstant;
import com.summer.dto.EmployeeLoginDTO;
import com.summer.entity.Employee;
import com.summer.properties.JwtProperties;
import com.summer.result.Result;
import com.summer.service.EmployeeService;
import com.summer.utils.JwtUtil;
import com.summer.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "用户认证")
@Slf4j
@RequestMapping("/admin/auth")
public class AuthController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JwtProperties jwtProperties;

    @ApiOperation("登录")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody @Validated EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录: {}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(jwtProperties.getAdminSecretKey(), jwtProperties.getAdminTtl(), claims);

        EmployeeLoginVO vo = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(vo);
    }

    @ApiOperation("退出")
    @PostMapping("/logout")
    public Result<Object> logout() {
        return Result.success();
    }
}
