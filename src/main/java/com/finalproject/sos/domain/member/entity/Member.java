package com.finalproject.sos.domain.member.entity;


import com.finalproject.sos.domain.auth.entity.SignIn;
import com.finalproject.sos.domain.auth.entity.SocialAccount;
import com.finalproject.sos.domain.common.entity.TimeStamped;
import jakarta.persistence.*;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 12, nullable = false)
    private String koreanName;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Column
    private String slackId;

    @Column
    @Enumerated(EnumType.STRING)
    private RoleType roleType;


    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "sign_in_id", unique = true)
    private SignIn signIn;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "social_account_Id", unique = true)
    private SocialAccount socialAccount;


    @Builder
    private Member(String koreanName, LocalDate birthDate, String slackId, RoleType roleType) {
        this.koreanName = koreanName;
        this.birthDate = birthDate;
        this.slackId = slackId;
        this.roleType = roleType;
    }

    public void linkSignIn(SignIn signIn){

        if(this.signIn != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"이미 가입된 이용자입니다.");
        }

        this.signIn = signIn;
    }
}
