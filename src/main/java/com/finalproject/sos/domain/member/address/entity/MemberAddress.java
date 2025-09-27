package com.finalproject.sos.domain.member.address.entity;


import com.finalproject.sos.domain.common.entity.TimeStamped;
import com.finalproject.sos.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAddress extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberAddressId;

    @Column(nullable = false)
    private String addressName;

    @Column(nullable = false)
    private BigDecimal lat;

    @Column(nullable = false)
    private BigDecimal lon;

    @Column(nullable = false)
    private String kakaoPlaceId;

    @OneToOne(mappedBy = "memberAddress", fetch = FetchType.LAZY)
    private Member member;

    public MemberAddress(String addressName, BigDecimal lon, BigDecimal lat, String kakaoPlaceId){
        this.addressName = addressName;
        this.lon = lon;
        this.lat = lat;
        this.kakaoPlaceId = kakaoPlaceId;
    }

    public void updateMemberAddress(String addressName, BigDecimal lon, BigDecimal lat, String kakaoPlaceId) {
        this.addressName = addressName;
        this.lon = lon;
        this.lat = lat;
        this.kakaoPlaceId = kakaoPlaceId;
    }
}
