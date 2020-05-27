package com.xiaobitipao.dungbeetle.core.enumeration;

/**
 * 登录类型: 微信登录/邮箱登录
 */
public enum LoginType {
    // 这里可以类比于类的实例化
    USER_WX(0, "微信登录"),
    USER_Email(1, "邮箱登录");

    private Integer value;

    /**
     * 这里的 description 只是用来描述，并不使用，所以可以不定义 description 成员变量
     */
    LoginType(Integer value, String description) {
        this.value = value;
    }
}
