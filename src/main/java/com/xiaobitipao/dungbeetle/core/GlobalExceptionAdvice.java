package com.xiaobitipao.dungbeetle.core;

import com.xiaobitipao.dungbeetle.core.configuration.ExceptionCodeConfiguration;
import com.xiaobitipao.dungbeetle.exception.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * 全局异常处理
 * <p>
 * 用于不过全局异常，使得返回给调用者的格式是固定的。
 */
@ControllerAdvice
public class GlobalExceptionAdvice {

    @Autowired
    private ExceptionCodeConfiguration codeConfiguration;

    /**
     * 全局 Exception 异常处理
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyResponse handleException(HttpServletRequest req, Exception e) {
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        // TODO: log
        System.out.println(e);
        UnifyResponse response = new UnifyResponse(9999, codeConfiguration.getMessage(9999), method + " " + requestUrl);
        return response;
    }

    /**
     * 全局 HttpException 异常处理
     */
    @ExceptionHandler(HttpException.class)
    public ResponseEntity<UnifyResponse> handleHttpException(HttpServletRequest req, HttpException e) {
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        UnifyResponse response = new UnifyResponse(e.getCode(), codeConfiguration.getMessage(e.getCode()), method + " " + requestUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatusCode());

        // 与直接返回一个 Object 相比返回 ResponseEntity 更灵活
        // 可以通过 ResponseEntity 设置 header 和 httpStatus 等属性
        ResponseEntity<UnifyResponse> entity = new ResponseEntity<>(response, headers, httpStatus);
        return entity;
    }

    /**
     * body 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public UnifyResponse handleBeanValidation(HttpServletRequest req, MethodArgumentNotValidException e) {
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();

        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String message = this.formatAllErrorMessages(errors);

        return new UnifyResponse(10001, message, method + " " + requestUrl);
    }

    /**
     * 查询参数校验异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public UnifyResponse handleConstraintException(HttpServletRequest req, ConstraintViolationException e) {
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        // 如果 e.getMessage() 返回的消息格式不满足需求的话
        // 需要通过循环 e.getConstraintViolations() 并调用 getMessage() 方法编辑消息
        String message = e.getMessage();
        return new UnifyResponse(10001, message, method + " " + requestUrl);
    }

    private String formatAllErrorMessages(List<ObjectError> errors) {
        StringBuffer errorMsg = new StringBuffer();
        errors.forEach(error -> errorMsg.append(error.getDefaultMessage()).append(';'));
        return errorMsg.toString();
    }
}
