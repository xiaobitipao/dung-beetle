package com.xiaobitipao.dungbeetle.dto.validators;

import com.xiaobitipao.dungbeetle.dto.PersonDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <pre>
 * 自定义校验器的处理类
 *
 * 该类必须要实现 ConstraintValidator<自定义注解, 自定义注解所修饰的目标类型(即注解打在哪个类上)> 接口
 * 如果注解修饰的字段的话，那么 ConstraintValidator 范型的第二个参数就应该是字段的类型
 * </pre>
 */
public class PasswordValidator implements ConstraintValidator<PasswordEqual, PersonDTO> {

    private int min;
    private int max;

    /**
     * 通过参数 constraintAnnotation 将注解信息传递过来
     */
    @Override
    public void initialize(PasswordEqual constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(PersonDTO personDTO, ConstraintValidatorContext constraintValidatorContext) {
        String password1 = personDTO.getPassword1();
        String password2 = personDTO.getPassword2();
//        boolean match = password1.equals(password2);
//        password1 < min
//                password11>max
        return password1.equals(password2);
    }
}
