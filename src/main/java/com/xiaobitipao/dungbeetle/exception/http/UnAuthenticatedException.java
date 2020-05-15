package com.xiaobitipao.dungbeetle.exception.http;

public class UnAuthenticatedException extends HttpException {
    public UnAuthenticatedException(int code) {
        this.httpStatusCode = 401;
        this.code = code;
    }
}
