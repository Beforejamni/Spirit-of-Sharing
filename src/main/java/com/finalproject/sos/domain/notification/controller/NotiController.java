package com.finalproject.sos.domain.notification.controller;

import com.finalproject.sos.domain.common.custom.CustomUserDetails;
import com.finalproject.sos.domain.notification.dto.response.NotiResponseDto;
import com.finalproject.sos.domain.notification.service.NotiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
