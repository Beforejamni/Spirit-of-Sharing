package com.finalproject.sos.domain.store.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreRequestDto {

    @NotBlank
    private String storeName;

    @NotBlank
    private String storeAddress;

    @NotBlank
    private String storeContact;


    @NotBlank
    private LocalTime startTime;

    @NotBlank
    private LocalTime endTime;

}
