package com.finalproject.sos.domain.order.controller;


import com.finalproject.sos.domain.common.custom.CustomUserDetails;
import com.finalproject.sos.domain.order.dto.response.CancelOrderResponseDto;
import com.finalproject.sos.domain.order.dto.response.OrderResponseDto;
import com.finalproject.sos.domain.order.entity.OrderStatus;
import com.finalproject.sos.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/update/{orderId}")
    public ResponseEntity<OrderResponseDto> updateOrderStatusBySeller(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                      @PathVariable Long orderId,
                                                                      @RequestParam OrderStatus orderStatus) {

        return ResponseEntity.ok().body(orderService.updateOrderStatusBySeller(userDetails, orderId, orderStatus));
    }
}
