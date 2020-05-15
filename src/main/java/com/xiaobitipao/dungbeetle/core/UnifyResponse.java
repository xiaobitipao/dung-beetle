package com.xiaobitipao.dungbeetle.core;

import com.xiaobitipao.dungbeetle.exception.CreateSuccess;

/**
 * <pre>
 * 给调用方的统一响应类
 * </pre>
 */
public class UnifyResponse {

    private int code;
    private String message;
    private String request;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getRequest() {
        return request;
    }

    public UnifyResponse(int code, String message, String request) {
        this.code = code;
        this.message = message;
        this.request = request;
    }

    public static void createSuccess(int code) {
        throw new CreateSuccess(code);
    }
}
