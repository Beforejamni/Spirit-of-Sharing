package com.finalproject.sos.domain.store.controller;


import com.finalproject.sos.domain.common.custom.CustomUserDetails;
import com.finalproject.sos.domain.store.dto.request.StoreRequestDto;
import com.finalproject.sos.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller/store")
@RequiredArgsConstructor
public class SellerStoreController {


    private final StoreService storeService;

    @PostMapping()
    public ResponseEntity<String> saveStore(@AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody StoreRequestDto requestDto) {

        storeService.saveStore(requestDto, userDetails);

        return ResponseEntity.ok("가게 생성 완료");
    }
}
