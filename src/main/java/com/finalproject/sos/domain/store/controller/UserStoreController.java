package com.finalproject.sos.domain.store.controller;


import com.finalproject.sos.domain.store.dto.response.StoreResponseDto;
import com.finalproject.sos.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member/store")
public class UserStoreController {

    private final StoreService storeService;

    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponseDto> readByStoreId (@PathVariable Long storeId) {

        StoreResponseDto storeResponseDto = storeService.readByStoreId(storeId);

        return ResponseEntity.ok().body(storeResponseDto);
    }
}
