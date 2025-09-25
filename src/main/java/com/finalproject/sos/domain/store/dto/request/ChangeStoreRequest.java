package com.finalproject.sos.domain.store.dto.request;

import lombok.Getter;

import java.time.LocalTime;


@Getter
public class ChangeStoreRequest {

    private String storeName;

    private String storeAddress;

    private String storeContact;

    private LocalTime startTime;

    private LocalTime endTime;
}
