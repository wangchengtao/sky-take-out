package com.summer.service.impl;

import com.summer.constant.MessageConstant;
import com.summer.constant.StatusConstant;
import com.summer.dto.EmployeeLoginDTO;
import com.summer.entity.Employee;
import com.summer.execption.AccountLockedException;
import com.summer.execption.AccountNotFoundException;
import com.summer.execption.PasswordErrorException;
import com.summer.mapper.EmployeeMapper;
import com.summer.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;


    @Override
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        Employee employee = employeeMapper.getByUsername(username);

        if (Objects.isNull(employee)) {
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        // 后续加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!employee.getPassword().equals(password)) {
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        return employee;
    }
}
