package com.finalproject.sos.domain.store.dto.response;


import com.finalproject.sos.domain.store.entity.StoreStatus;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class SellerStoreResponse {

    private String storeName;

    private String storeAddress;

    private String storeContact;

    private LocalTime startTime;

    private LocalTime endTime;

    private StoreStatus status;


    public SellerStoreResponse(String storeName, String storeAddress, String storeContact, LocalTime startTime, LocalTime endTime, StoreStatus storeStatus) {
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.storeContact = storeContact;
        this.startTime = startTime;
        this. endTime = endTime;
        this.status = storeStatus;
    }
}
