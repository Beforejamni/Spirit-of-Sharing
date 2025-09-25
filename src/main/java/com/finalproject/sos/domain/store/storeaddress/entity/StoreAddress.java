package com.finalproject.sos.domain.store.storeaddress.entity;

import com.finalproject.sos.domain.common.entity.TimeStamped;
import com.finalproject.sos.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreAddress extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeAddressId;


    @Column(nullable = false)
    private String storeAddress;

    //위도
    @Column(nullable = false)
    private BigDecimal lat;

    //경도
    @Column(nullable = false)
    private BigDecimal lon;

    @Column(nullable = true)
    private String addressNum;

    @Column(nullable = false)
    private String kakaoPlaceId;

    @OneToOne(mappedBy = "storeAddress",fetch = FetchType.LAZY,optional = true)
    private Store store;

    @Builder
    public StoreAddress(String storeAddress, BigDecimal lat, BigDecimal lon, String kakaoPlaceId){
        this.storeAddress = storeAddress;
        this.lat = lat;
        this.lon = lon;
        this.kakaoPlaceId = kakaoPlaceId;
    }

}
