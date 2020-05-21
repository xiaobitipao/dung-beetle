package com.xiaobitipao.dungbeetle.dto.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 自定义密码和确认密码校验注解
 *
 * 注解本身是不能完成业务处理的，需要通过 @Constraint 注解指定哪一个类进行处理。
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordEqual {

    // 参数设定: 用于用户传入数据
    // 在注解里需要使用基本类型 int 不能使用包装类型 Integer
    int min() default 6;

    int max() default 32;

    String message() default "passwords are not equal";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
