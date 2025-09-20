package com.finalproject.sos.validatorTest;


import com.finalproject.sos.domain.common.annotation.Adult;
import com.finalproject.sos.domain.common.validator.AdultValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AdultValidatorTest {


    private AdultValidator adultValidator;

    @BeforeEach
    void setDay() {

        Clock setClock = Clock.fixed(
                LocalDate.of(2025, 10, 3).atStartOfDay(ZoneId.of("Asia/Seoul"))
                        .toInstant(),ZoneId.of("Asia/Seoul")
        );

        adultValidator = new AdultValidator(setClock);

        Adult annotation = mock(Adult.class);
        when(annotation.minYears()).thenReturn(19);
        when(annotation.message()).thenReturn("만 19세 이상만 가능합니다.");

        adultValidator.initialize(annotation);
    }


    @Test
    void 만19세이상이면_true() {
        assertThat(adultValidator.isValid(LocalDate.of(1999,9,4), null)).isTrue();
    }

    @Test
    void 만19세미만이면_false() {
        assertThat(adultValidator.isValid(LocalDate.of(2009,10,11), null)).isFalse();
    }
}
