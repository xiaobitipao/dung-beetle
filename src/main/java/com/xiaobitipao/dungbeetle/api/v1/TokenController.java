package com.xiaobitipao.dungbeetle.api.v1;

import com.xiaobitipao.dungbeetle.dto.TokenCreateDTO;
import com.xiaobitipao.dungbeetle.dto.TokenVerifyDTO;
import com.xiaobitipao.dungbeetle.exception.http.NotFoundException;
import com.xiaobitipao.dungbeetle.service.WxAuthenticationService;
import com.xiaobitipao.dungbeetle.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "token")
@RestController
public class TokenController {

    @Autowired
    private WxAuthenticationService wxAuthenticationService;

    /**
     * 用户登录获取 token
     */
    @PostMapping("")
    public Map<String, String> getToken(@RequestBody @Validated TokenCreateDTO userData) {
        String token = null;
        switch (userData.getType()) {
            case USER_WX:
                token = wxAuthenticationService.code2Session(userData.getAccount());
                break;
            case USER_Email:
                break;
            default:
                throw new NotFoundException(10003);
        }
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }

    /**
     * 校验 token
     */
    @PostMapping("/verify")
    public Map<String, Boolean> verify(@RequestBody TokenVerifyDTO token) {
        Boolean isValid = JwtToken.verifyToken(token.getToken());
        Map<String, Boolean> map = new HashMap<>();
        map.put("is_valid", isValid);
        return map;
    }
}
