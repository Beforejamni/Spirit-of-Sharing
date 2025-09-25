package com.finalproject.sos.domain.store.dto.response;

import com.finalproject.sos.domain.store.entity.StoreStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreResponseDto {

    private String storeName;

    private String storeAddress;

    private String storeContact;

    private LocalTime startTime;

    private LocalTime endTime;

    private StoreStatus status;


    public StoreResponseDto(String storeName, String storeAddress, String storeContact, LocalTime startTime, LocalTime endTime, StoreStatus storeStatus) {
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.storeContact = storeContact;
        this.startTime = startTime;
        this. endTime = endTime;
        this.status = storeStatus;
    }
}
