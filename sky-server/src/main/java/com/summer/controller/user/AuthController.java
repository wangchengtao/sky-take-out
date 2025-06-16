package com.summer.controller.user;

import com.summer.constant.JwtClaimsConstant;
import com.summer.dto.UserLoginDTO;
import com.summer.entity.User;
import com.summer.properties.JwtProperties;
import com.summer.result.Result;
import com.summer.service.UserService;
import com.summer.utils.JwtUtil;
import com.summer.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("userAuthController")
@RequestMapping("/user/auth")
@Api(tags = "用户-授权")
@Slf4j
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/login")
    @ApiOperation("微信登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("微信登录: {}", userLoginDTO);

        User user = userService.wxLogin(userLoginDTO);

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);


        UserLoginVO vo = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();

        return Result.success(vo);
    }
}
