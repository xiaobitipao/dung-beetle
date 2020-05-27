package com.xiaobitipao.dungbeetle.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaobitipao.dungbeetle.exception.http.ParameterException;
import com.xiaobitipao.dungbeetle.exception.http.UnAuthenticatedException;
import com.xiaobitipao.dungbeetle.model.User;
import com.xiaobitipao.dungbeetle.repository.UserRepository;
import com.xiaobitipao.dungbeetle.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class WxAuthenticationService {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Value("${wx.code2SessionUrl}")
    private String code2SessionUrl;

    @Value("${wx.appid}")
    private String appid;

    @Value("${wx.appsecret}")
    private String appsecret;

    /**
     * 登录凭证校验
     *
     * @param code 微信小程序在微信登录后获取的 code
     */
    public String code2Session(String code) {
        String url = MessageFormat.format(this.code2SessionUrl, this.appid, this.appsecret, code);
        RestTemplate rest = new RestTemplate();
        String sessionText = rest.getForObject(url, String.class);
        Map<String, Object> session = new HashMap<>();
        try {
            session = mapper.readValue(sessionText, Map.class);
        } catch (JsonProcessingException e) {
            // TODO: log
            e.printStackTrace();
            throw new UnAuthenticatedException(10006);
        }
        return this.registerUser(session);
    }

    /**
     * 根据微信的登录凭证校验的返回结果生成 token
     */
    private String registerUser(Map<String, Object> session) {
        // 从登录凭证校验获取 openid
        String openid = (String) session.get("openid");
        if (openid == null) {
            throw new ParameterException(20004);
        }

        // 根据 openid 从数据库 user 表获取 uid
        Optional<User> userOptional = this.userRepository.findByOpenid(openid);

        // 因为无论是否能够获取到 user 信息都要执行业务逻辑，所以这里使用 orElseThrow 不太合适
        // 如果是 jdk11 的话可以使用 userOptional.ifPresentOrElse(Consummer, Runable)
        if (userOptional.isPresent()) {
            return JwtToken.makeToken(userOptional.get().getId());
        }

        // 如果没有从数据库获取到 uid，说明以前用户没有登录过，将用户信息登录至数据库的 user 表并返回 token
        User user = User.builder().openid(openid).build();
        userRepository.save(user);
        return JwtToken.makeToken(user.getId());
    }
}
