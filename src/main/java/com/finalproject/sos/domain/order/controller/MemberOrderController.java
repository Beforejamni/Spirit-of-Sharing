package com.finalproject.sos.domain.order.controller;


import com.finalproject.sos.domain.common.custom.CustomUserDetails;
import com.finalproject.sos.domain.order.dto.response.CreatedOrderResponse;
import com.finalproject.sos.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member/order")
public class MemberOrderController {

    private final OrderService orderService;

    @PostMapping("/{itemId}")
    public ResponseEntity<CreatedOrderResponse> saveOrder(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                          @PathVariable Long itemId,
                                                          @RequestParam Integer itemCnt) {

        return new ResponseEntity<>(orderService.saveOrder(userDetails, itemId, itemCnt), HttpStatus.CREATED);
    }
}
