package com.summer.service;

import com.summer.dto.EmployeeDTO;
import com.summer.dto.EmployeeLoginDTO;
import com.summer.entity.Employee;

public interface EmployeeService {

    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void save(EmployeeDTO employeeDTO);
}
