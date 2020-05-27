package com.xiaobitipao.dungbeetle.dto.validators;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <pre>
 * 自定义校验器注解的处理类
 *
 * 该类必须要实现 ConstraintValidator<自定义注解, 自定义注解所修饰的目标类型(即注解打在哪个类上)> 接口
 * 如果注解修饰的是字段的话，那么 ConstraintValidator 范型的第二个参数就应该是字段的类型
 * </pre>
 */
public class TokenPasswordValidator implements ConstraintValidator<TokenPassword, String> {

    private int min;
    private int max;

    /**
     * 通过参数 constraintAnnotation 将注解信息传递过来
     */
    @Override
    public void initialize(TokenPassword constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(s)) {
            // 比如小程序就不需要传入密码
            return true;
        }
        return s.length() >= this.min && s.length() <= this.max;
    }
}
