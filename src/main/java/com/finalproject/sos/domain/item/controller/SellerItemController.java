package com.finalproject.sos.domain.item.controller;

import com.finalproject.sos.domain.common.custom.CustomUserDetails;
import com.finalproject.sos.domain.item.dto.request.ItemRequestDto;
import com.finalproject.sos.domain.item.dto.response.ItemResponseDto;
import com.finalproject.sos.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller/item")
public class SellerItemController {

    private final ItemService itemService;

    @PostMapping("/{whiskeyId}")
    public ResponseEntity<ItemResponseDto> saveItem(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                    @PathVariable Long whiskeyId,
                                                    @RequestBody ItemRequestDto requestDto) {


        return new ResponseEntity<>(itemService.saveItem(userDetails, whiskeyId, requestDto), HttpStatus.CREATED);
    }

    @PostMapping("/update/{itemId}")
    public ResponseEntity<ItemResponseDto> updateItem(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                      @PathVariable Long itemId,
                                                      @RequestBody ItemRequestDto requestDto) {


        return  ResponseEntity.ok(itemService.updateItem(userDetails, itemId, requestDto));
    }

    @PostMapping("/delete/{itemId}")
    public ResponseEntity<Void> deleteItem(@AuthenticationPrincipal CustomUserDetails userDetails,
                                           @PathVariable Long itemId){

        itemService.deleteItem(userDetails, itemId);

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponseDto> readBySeller(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                        @PathVariable Long itemId) {

        return ResponseEntity.ok().body(itemService.readBySeller(userDetails ,itemId));
    }

}
