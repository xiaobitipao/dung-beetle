package com.xiaobitipao.dungbeetle.exception;

import com.xiaobitipao.dungbeetle.exception.http.HttpException;

public class UpdateSuccess extends HttpException {
    public UpdateSuccess(int code) {
        this.httpStatusCode = 200;
        this.code = code;
    }
}
