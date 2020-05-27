package com.xiaobitipao.dungbeetle.service;

import com.xiaobitipao.dungbeetle.dto.TokenCreateDTO;
import org.springframework.stereotype.Service;

/**
 * 认证服务: 当前使用微信认证，所以当前的 Service 暂时不用
 */
@Service
public class AuthenticationService {

    public void getTokenByEmail(TokenCreateDTO userData) {
    }

    public void validateByWx(TokenCreateDTO userData) {
    }

    public void register() {
    }
}
