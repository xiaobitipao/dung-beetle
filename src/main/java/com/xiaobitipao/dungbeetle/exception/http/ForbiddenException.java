package com.xiaobitipao.dungbeetle.exception.http;

public class ForbiddenException extends HttpException {
    public ForbiddenException(int code) {
        this.httpStatusCode = 403;
        this.code = code;
    }
}
