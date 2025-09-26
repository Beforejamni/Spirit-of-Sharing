package com.finalproject.sos.domain.store.entity;

import com.finalproject.sos.domain.common.entity.TimeStamped;
import com.finalproject.sos.domain.item.entity.Item;
import com.finalproject.sos.domain.member.entity.Member;
import com.finalproject.sos.domain.store.storeaddress.entity.StoreAddress;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Where(clause = "is_deleted = false")
public class Store extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private String storeContact;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StoreStatus storeStatus;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = true)
    private boolean isDeleted;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", unique = true)
    private Member member;

    //추후 연결
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_address_id", unique = true, nullable = true)
    private StoreAddress storeAddress;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Item> itemList = new ArrayList<>();


//    @Builder
//    public Store(String storeName, String storeContact, StoreStatus storeStatus, LocalTime startTime, LocalTime endTime, Member member, StoreAddress storeAddress) {
//        this.storeName = storeName;
//        this.storeContact = storeContact;
//        this.storeStatus = storeStatus;
//        this.startTime = startTime;
//        this.endTime = endTime;
//        this.member = member;
//        this.storeAddress = storeAddress;
//    }

    public void changeStore(String storeName, String storeContact, LocalTime startTime, LocalTime endTime){

        this.storeName = (storeName != null) ? storeName : this.storeName;
        this.storeContact = (storeContact != null) ? storeContact : this.storeContact;
        this.startTime = (startTime != null) ? startTime : this.startTime;
        this.endTime = (endTime != null) ? endTime : this.endTime;
    }

    public void changeStoreStatus() {

        this.storeStatus = (this.storeStatus == StoreStatus.PICKUP_DISABLE)
                ? StoreStatus.PICKUP_ABLE : StoreStatus.PICKUP_DISABLE;
    }

    public void setIsDeleted(){
        this.isDeleted = true;
    }
}
