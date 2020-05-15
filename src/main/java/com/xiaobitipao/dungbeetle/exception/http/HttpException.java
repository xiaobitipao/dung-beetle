package com.xiaobitipao.dungbeetle.exception.http;

/**
 * <pre>
 * Http 异常基类
 *
 * 扩展了以下两个成员变量
 * code: 业务处理结果代码
 * httpStatusCode: Http 状态码
 * </pre>
 */
public class HttpException extends RuntimeException {
    protected Integer code;
    protected Integer httpStatusCode = 500;

    public Integer getCode() {
        return code;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }
}
