package com.xiaobitipao.dungbeetle.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 校验 token 时前端传递的参数类型
 */
@Getter
@Setter
public class TokenVerifyDTO {
    private String token;
}
