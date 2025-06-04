package com.summer.service;

import com.summer.dto.EmployeeLoginDTO;
import com.summer.entity.Employee;

public interface EmployeeService {

    Employee login(EmployeeLoginDTO employeeLoginDTO);
}
