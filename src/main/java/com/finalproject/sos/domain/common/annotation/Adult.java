package com.finalproject.sos.domain.common.annotation;

import com.finalproject.sos.domain.common.validator.AdultValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented // 문서화
@Constraint(validatedBy = AdultValidator.class) // 유효성 검증 선언
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Adult {

    int minYears() default  19;

    String message() default "만 19세 이상만 가능합니다.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
