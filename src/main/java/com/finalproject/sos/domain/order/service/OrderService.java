package com.finalproject.sos.domain.order.service;


import com.finalproject.sos.domain.common.custom.CustomUserDetails;
import com.finalproject.sos.domain.item.entity.Item;
import com.finalproject.sos.domain.item.repository.ItemRepository;
import com.finalproject.sos.domain.member.entity.Member;
import com.finalproject.sos.domain.member.repository.MemberRepository;
import com.finalproject.sos.domain.order.dto.response.CancelOrderResponseDto;
import com.finalproject.sos.domain.order.dto.response.CreatedOrderResponse;
import com.finalproject.sos.domain.order.entity.Order;
import com.finalproject.sos.domain.order.entity.OrderStatus;
import com.finalproject.sos.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public CreatedOrderResponse saveOrder(CustomUserDetails userDetails, Long itemId, Integer itemCnt) {

        Long meId = userDetails.getMemberId();

        Member member = memberRepository.findById(meId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."));


        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 물품을 찾을 수 없습니다."));

        int rest = item.getItemCnt()- itemCnt;

        if( rest < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "재고가 부족합니다.");
        }

        //같은 랜덤 값이 출력될 수 있음.
        String orderNum = createOrderNum();

        BigDecimal orderPrice = item.getItemPrice().multiply(new BigDecimal(itemCnt));

        Order order = new Order(orderNum, itemCnt, orderPrice, null, OrderStatus.WAITING_ORDER, member, item );

        orderRepository.save(order);

        item.restItemCnt(rest);

        return CreatedOrderResponse.builder().order(order).build();
    }

    @Transactional
    public CancelOrderResponseDto cancelOrder(CustomUserDetails userDetails, Long orderId) {

        Long meId = userDetails.getMemberId();

        Member member = memberRepository.getReferenceById(meId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 주문을 찾을 수 없습니다."));

        if(order.getMember() != member){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "내 주문이 아닙니다.");
        }

        switch (order.getOrderStatus()) {
            case WAITING_ORDER -> order.cancelOrder();
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "취소 가능 상태가 아닙니다.");
        }

        return CancelOrderResponseDto.builder().order(order).build();
    }

    @Transactional
    public CancelOrderResponseDto cancelOrderBySeller(CustomUserDetails userDetails, Long orderId) {

        Long ownerId = userDetails.getMemberId();

        Member member = memberRepository.getReferenceById(ownerId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 주문을 찾을 수 없습니다."));

        if(order.getStore().getMember() != member) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "내 가게 주문이 아닙니다.");
        }

        switch (order.getOrderStatus()) {
            case COMPLETED_PICKUP, CANCELED_ORDER -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"취소 가능 상태가 아닙니다.");
            default -> order.cancelOrder();
        }

        return CancelOrderResponseDto.builder().order(order).build();
    }


    private String createOrderNum() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        StringBuilder sb = new StringBuilder(10);

        for(int i = 0; i < 10; i++) {
            int randomindex = (int)(Math.random() * 36);
            sb.append(chars.charAt(randomindex));
        }

        return sb.toString();
    }


}
