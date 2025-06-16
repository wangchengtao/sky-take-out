package com.summer.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.summer.constant.MessageConstant;
import com.summer.dto.UserLoginDTO;
import com.summer.entity.User;
import com.summer.execption.LoginFailedException;
import com.summer.mapper.UserMapper;
import com.summer.properties.WeChatProperties;
import com.summer.service.UserService;
import com.summer.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {

    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WeChatProperties wechatProperties;

    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
        String openid = getOpenid(userLoginDTO.getCode());

        if (openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        User user = userMapper.getByOpenid(openid);

        if (user == null) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();

            userMapper.insert(user);
        }

        return user;

    }

    private String getOpenid(String code) {
        HashMap<String, String> map = new HashMap<>();
        map.put("appid", wechatProperties.getAppid());
        map.put("secret", wechatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");

        String json = HttpClientUtil.doGet(WX_LOGIN, map);
        JSONObject jsonObject = JSON.parseObject(json);

        return jsonObject.getString("openid");
    }
}
