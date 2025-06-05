package com.summer.service;

import com.summer.dto.EmployeeDTO;
import com.summer.dto.EmployeeLoginDTO;
import com.summer.dto.EmployeePageQueryDTO;
import com.summer.entity.Employee;
import com.summer.result.PageResult;

public interface EmployeeService {

    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void save(EmployeeDTO employeeDTO);

    PageResult<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    void startOrStop(Integer status, Long id);

    Employee getById(Long id);

    void update(EmployeeDTO employeeDTO);
}
