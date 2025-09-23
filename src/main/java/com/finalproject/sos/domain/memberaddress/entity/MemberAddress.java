package com.finalproject.sos.domain.memberaddress.entity;


import com.finalproject.sos.domain.common.entity.TimeStamped;
import jakarta.persistence.*;

@Entity
public class MemberAddress extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberAddressId;

    @Column
    private String memberAddress;

    //위도
    @Column
    private double lat;

    //경도
    @Column
    private double lon;

    @Column
    private int addressNum;

    @Column
    private int radius;


}
