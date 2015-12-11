package com.gces.budget.domain.customvalidator.annotation;

import com.gces.budget.domain.customvalidator.validator.PasswordMatchesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;


/**
 * Created by minamrosh on 12/5/15.
 */
@Target({TYPE,ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {
    String message() default "Sorry, Password doesn't match!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}
