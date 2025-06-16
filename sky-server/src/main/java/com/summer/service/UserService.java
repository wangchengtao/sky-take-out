package com.summer.service;

import com.summer.dto.UserLoginDTO;
import com.summer.entity.User;

public interface UserService {
    User wxLogin(UserLoginDTO userLoginDTO);
}
