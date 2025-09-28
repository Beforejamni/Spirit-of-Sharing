package com.finalproject.sos.domain.auth.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.springframework.security.core.parameters.P;

@Getter
public class UpdatePasswordRequest {

    private String currentPassword;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,64}$",
            message = "비밀번호는 영문 대/소문자, 숫자, 특수기호를 하나씩 포함해야 하고 8글자 이상이어야 합니다.")
    private String newPassword;
}
