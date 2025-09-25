package com.finalproject.sos.domain.store.controller;


import com.finalproject.sos.domain.common.custom.CustomUserDetails;
import com.finalproject.sos.domain.store.dto.request.ChangeStoreRequest;
import com.finalproject.sos.domain.store.dto.request.StoreRequestDto;
import com.finalproject.sos.domain.store.dto.response.SellerStoreResponse;
import com.finalproject.sos.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.init.RepositoriesPopulatedEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller/store")
@RequiredArgsConstructor
public class SellerStoreController {


    private final StoreService storeService;

    @PostMapping()
    public ResponseEntity<String> saveStore(@AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody StoreRequestDto requestDto) {

        storeService.saveStore(requestDto, userDetails);

        return new ResponseEntity<>("가게 생성 완료", HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<SellerStoreResponse> readByOwner(@AuthenticationPrincipal CustomUserDetails userDetails) {

        SellerStoreResponse sellerStoreResponse = storeService.readByOwner(userDetails);

        return ResponseEntity.ok().body(sellerStoreResponse);
    }

    @PostMapping("/change")
    public ResponseEntity<SellerStoreResponse> changeStore(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                           @RequestBody ChangeStoreRequest storeRequest) {

        SellerStoreResponse sellerStoreResponse = storeService.changeStore(userDetails, storeRequest);

        return ResponseEntity.ok().body(sellerStoreResponse);
    }

    @PostMapping("/pickupable")
    public ResponseEntity<SellerStoreResponse> pickupableStore(@AuthenticationPrincipal CustomUserDetails userDetails) {


        SellerStoreResponse sellerStoreResponse = storeService.pickupableStore(userDetails);

        return ResponseEntity.ok().body(sellerStoreResponse);
    }
}
