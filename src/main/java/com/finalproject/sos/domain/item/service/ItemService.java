package com.finalproject.sos.domain.item.service;

import com.finalproject.sos.domain.common.custom.CustomUserDetails;
import com.finalproject.sos.domain.item.dto.request.ItemRequestDto;
import com.finalproject.sos.domain.item.dto.response.ItemResponseDto;
import com.finalproject.sos.domain.item.entity.Item;
import com.finalproject.sos.domain.item.repository.ItemRepository;
import com.finalproject.sos.domain.member.entity.Member;
import com.finalproject.sos.domain.member.repository.MemberRepository;
import com.finalproject.sos.domain.store.entity.Store;
import com.finalproject.sos.domain.store.repository.StoreRepository;
import com.finalproject.sos.domain.whiskey.entity.Whiskey;
import com.finalproject.sos.domain.whiskey.repository.WhiskeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final StoreRepository storeRepository;
    private final WhiskeyRepository whiskeyRepository;

    @Transactional
    public ItemResponseDto saveItem(CustomUserDetails userDetails, Long whiskeyId, ItemRequestDto requestDto) {

        Long ownerId = userDetails.getMemberId();

        Member member = memberRepository.findById(ownerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저가 없습니다."));

        Store store = storeRepository.findByMember(member)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 가게가 없습니다."));

        Whiskey whiskey = whiskeyRepository.findById(whiskeyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 위스키가 없습니다."));

        Item item = Item.builder().whiskey(whiskey).store(store).itemRequestDto(requestDto).build();

        itemRepository.save(item);

        return ItemResponseDto.builder().item(item).build();
    }

    @Transactional(readOnly = true)
    public ItemResponseDto readByMember(Long itemId) {

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 물품이 존재하지 않습니다."));


        return ItemResponseDto.builder().item(item).build();
    }

    @Transactional(readOnly = true)
    public ItemResponseDto readBySeller(CustomUserDetails userDetails, Long itemId) {

        Long ownerId = userDetails.getMemberId();

        Member member = memberRepository.getReferenceById(ownerId);

        Store store = storeRepository.findByMember(member)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 가게가 존재하지 않습니다."));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 물품이 존재하지 않습니다."));

        if(!store.getStoreName().matches(item.getStore().getStoreName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 가게의 물품이 아닙니다.");
        }

        return ItemResponseDto.builder().item(item).build();
    }
}
