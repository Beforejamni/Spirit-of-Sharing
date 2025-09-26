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
}
