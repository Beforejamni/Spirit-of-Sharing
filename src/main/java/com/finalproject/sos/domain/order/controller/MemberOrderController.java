package com.finalproject.sos.domain.order.controller;


import com.finalproject.sos.domain.common.custom.CustomUserDetails;
import com.finalproject.sos.domain.order.dto.response.CancelOrderResponseDto;
import com.finalproject.sos.domain.order.dto.response.CreatedOrderResponse;
import com.finalproject.sos.domain.order.dto.response.OrderResponseDto;
import com.finalproject.sos.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
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

    @PostMapping("cancel/{orderId}")
    public ResponseEntity<CancelOrderResponseDto> cancelOrder(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                              @PathVariable Long orderId) {


        return ResponseEntity.ok().body(orderService.cancelOrder(userDetails, orderId));
    }

    @GetMapping()
    public ResponseEntity<Slice<OrderResponseDto>> readAllByMember(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                   @PageableDefault()Pageable pageable) {

        return ResponseEntity.ok().body(orderService.readAllByMember(userDetails, pageable));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> readByMember(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                         @PathVariable Long orderId) {

        return ResponseEntity.ok().body(orderService.readByMember(userDetails, orderId));
    }
}
