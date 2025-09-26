package com.finalproject.sos.domain.order.controller;


import com.finalproject.sos.domain.common.custom.CustomUserDetails;
import com.finalproject.sos.domain.order.dto.response.CancelOrderResponseDto;
import com.finalproject.sos.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller/order")
@RequiredArgsConstructor
public class SellerOrderController {

    private final OrderService orderService;

    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<CancelOrderResponseDto> cancelOrder(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                              @PathVariable Long orderId) {

        return ResponseEntity.ok().body(orderService.cancelOrderBySeller(userDetails, orderId));
    }
}
