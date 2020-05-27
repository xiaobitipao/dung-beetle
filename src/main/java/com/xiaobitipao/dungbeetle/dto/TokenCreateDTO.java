package com.xiaobitipao.dungbeetle.dto;

import com.xiaobitipao.dungbeetle.core.enumeration.LoginType;
import com.xiaobitipao.dungbeetle.dto.validators.TokenPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 生成(Login) token 时前端传递的参数类型
 */
@Getter
@Setter
public class TokenCreateDTO {
    @NotBlank(message = "token.account.required")
    private String account;

    @TokenPassword(min = 6, max = 32, message = "{token.password}")
    private String password;

    private LoginType type;
}
