package com.finalproject.sos.domain.common.validator;

import com.finalproject.sos.domain.common.annotation.Adult;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class AdultValidator implements ConstraintValidator<Adult, LocalDate> {


    private final Clock clock;

    private int minYears;


    @Override
    public void initialize(Adult constraintAnnotation) {
        this.minYears = constraintAnnotation.minYears();
    }

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext constraintValidatorContext) {

        //@NotNull 로 처리
        if (birthDate == null) {
            return true;
        }

        LocalDate today = LocalDate.now(clock);
        LocalDate minDay = today.minusYears(minYears);

        return !birthDate.isAfter(minDay);
    }
}
