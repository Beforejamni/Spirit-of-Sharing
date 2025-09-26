package com.finalproject.sos.domain.order.service;


import com.finalproject.sos.domain.common.custom.CustomUserDetails;
import com.finalproject.sos.domain.item.entity.Item;
import com.finalproject.sos.domain.item.repository.ItemRepository;
import com.finalproject.sos.domain.member.entity.Member;
import com.finalproject.sos.domain.member.repository.MemberRepository;
import com.finalproject.sos.domain.order.dto.response.CreatedOrderResponse;
import com.finalproject.sos.domain.order.entity.Order;
import com.finalproject.sos.domain.order.entity.OrderStatus;
import com.finalproject.sos.domain.order.repository.OrderRepository;
import com.finalproject.sos.domain.store.entity.Store;
import com.finalproject.sos.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

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
