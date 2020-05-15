package com.xiaobitipao.dungbeetle.exception;

import com.xiaobitipao.dungbeetle.exception.http.HttpException;

public class DeleteSuccess extends HttpException {
    public DeleteSuccess(int code) {
        this.httpStatusCode = 200;
        this.code = code;
    }
}
