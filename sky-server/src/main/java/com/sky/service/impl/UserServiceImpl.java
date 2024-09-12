package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private WeChatProperties weChatProperties;
    private static final String WECHAT_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";
    @Autowired
    private UserMapper userMapper;
    public User wxlogin(UserLoginDTO userLoginDTO) {
        //调用微信登录接口
        Map<String, String> params = new HashMap<>();
        params.put("appid", weChatProperties.getAppid());
        params.put("secret", weChatProperties.getSecret());
        params.put("js_code", userLoginDTO.getCode());
        params.put("grant_type", "authorization_code");
         String result = HttpClientUtil.doGet(WECHAT_LOGIN_URL, params);
         JSONObject json = JSONObject.parseObject(result);
         String openid = json.getString("openid");
         log.info("openid:{}", openid);
         //判断openid是否为空
         if (openid == null) {
             throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
         }

        //判断用户是否存在
         User user = userMapper.selectByOpenid(openid);

         if (user == null) {
             user = User.builder().openid(openid).createTime(LocalDateTime.now()).build();
             userMapper.insert(user);
         }
         //返回用户信息
        return user;
    }
}
