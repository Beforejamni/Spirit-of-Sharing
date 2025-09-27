package com.finalproject.sos.domain.member.entity;


import com.finalproject.sos.domain.auth.entity.SignIn;
import com.finalproject.sos.domain.common.entity.TimeStamped;
import com.finalproject.sos.domain.member.address.entity.MemberAddress;
import com.finalproject.sos.domain.notification.entity.Notification;
import com.finalproject.sos.domain.order.entity.Order;
import com.finalproject.sos.domain.store.entity.Store;
import jakarta.persistence.*;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

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

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private Store store;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_address_id")
    private MemberAddress memberAddress;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Order> orderList = new ArrayList<>();

    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
    private List<Notification> notiList = new ArrayList<>();

    public Member(String koreanName, LocalDate birthDate, String slackId, RoleType roleType) {
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

    public void updateMemberAddress(MemberAddress memberAddress) {
        this.memberAddress = memberAddress;
    }
}
