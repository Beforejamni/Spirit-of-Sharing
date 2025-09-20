package com.finalproject.sos.domain.member.entity;


import com.finalproject.sos.domain.auth.entity.SignIn;
import com.finalproject.sos.domain.common.entity.TimeStamped;
import jakarta.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
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

}
