package com.adp.payment.util;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = BillParamValidator.class)
@Target({ ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BillParamConstraint {

    String message() default "BillParamConstraint.invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};
}