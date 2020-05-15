package com.xiaobitipao.dungbeetle.exception;

import com.xiaobitipao.dungbeetle.exception.http.HttpException;

public class CreateSuccess extends HttpException {
    public CreateSuccess(int code) {
        this.httpStatusCode = 201;
        this.code = code;
    }
}
