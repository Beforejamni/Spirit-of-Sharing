package com.finalproject.sos.domain.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finalproject.sos.domain.auth.entity.SignIn;
import com.finalproject.sos.domain.common.annotation.Adult;
import com.finalproject.sos.domain.member.entity.Member;
import com.finalproject.sos.domain.member.entity.RoleType;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;


import java.time.LocalDate;

@Getter
@Builder
public class SignUpRequestDto {

    @NotBlank
    @Size(min = 3 , max = 12,  message = "아이디는 3~12자 사이여야합니다.")
    private String username;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,64}$",
            message = "비밀번호는 영문 대/소문자, 숫자, 특수기호를 하나씩 포함해야 하고 8글자 이상이어야 합니다.")
    private String password;

    @NotBlank
    private String koreanName;

    @NotNull(message = "생년월일이 필수 입니다.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Adult(message = "성인만 이용가능합니다.")
    private LocalDate birthDate;


    private String slackId;

    public SignIn toSignIn(String encodePw) {
        return SignIn.builder()
                .username(username)
                .password(encodePw)
                .build();
    }

    public Member toMember(RoleType roleType) {
        return  Member.builder()
                .koreanName(koreanName)
                .birthDate(birthDate)
                .slackId(slackId)
                .roleType(roleType)
                .build();
    }
}
