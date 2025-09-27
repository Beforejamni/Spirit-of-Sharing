package com.finalproject.sos.domain.notification.controller;

import com.finalproject.sos.domain.common.custom.CustomUserDetails;
import com.finalproject.sos.domain.item.dto.response.ItemResponseDto;
import com.finalproject.sos.domain.item.entity.Item;
import com.finalproject.sos.domain.notification.dto.response.NotiResponseDto;
import com.finalproject.sos.domain.notification.service.NotiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member/noti")
@RequiredArgsConstructor
public class NotiController {

    private final NotiService notiService;

    @PostMapping("/item/{itemId}")
    public ResponseEntity<NotiResponseDto> saveNoti(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                    @PathVariable Long itemId) {

        return ResponseEntity.ok().body(notiService.saveNoti(userDetails, itemId));
    }

    @GetMapping()
    public ResponseEntity<Slice<ItemResponseDto>> readAll(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                          @PageableDefault()Pageable pageable) {

        return ResponseEntity.ok().body(notiService.readAll(userDetails, pageable));
    }

}
