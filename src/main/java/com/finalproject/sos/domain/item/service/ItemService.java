package com.finalproject.sos.domain.item.service;

import com.finalproject.sos.domain.common.custom.CustomUserDetails;
import com.finalproject.sos.domain.item.dto.request.ItemRequestDto;
import com.finalproject.sos.domain.item.dto.response.ItemResponseDto;
import com.finalproject.sos.domain.item.dto.response.SearchByWhiskeyResponseDto;
import com.finalproject.sos.domain.item.entity.Item;
import com.finalproject.sos.domain.item.repository.ItemRepository;
import com.finalproject.sos.domain.member.entity.Member;
import com.finalproject.sos.domain.member.repository.MemberRepository;
import com.finalproject.sos.domain.store.dto.response.SimpleStoreResponseDto;
import com.finalproject.sos.domain.store.entity.Store;
import com.finalproject.sos.domain.store.repository.StoreRepository;
import com.finalproject.sos.domain.whiskey.entity.Whiskey;
import com.finalproject.sos.domain.whiskey.repository.WhiskeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

        Item item = new Item(requestDto, whiskey, store);

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

    @Transactional
    public ItemResponseDto updateItem(CustomUserDetails userDetails, Long itemId, ItemRequestDto requestDto) {

        Long ownerId = userDetails.getMemberId();

        Member member = memberRepository.getReferenceById(ownerId);

        Store store = storeRepository.findByMember(member)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 가게가 존재하지 않습니다."));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 물품이 존재하지 않습니다."));

        if(!store.getStoreName().matches(item.getStore().getStoreName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 가게의 물품이 아닙니다.");
        }

        item.updateItem(requestDto);

        return ItemResponseDto.builder().item(item).build();
    }

    @Transactional
    public void deleteItem(CustomUserDetails userDetails, Long itemId) {

        Long ownerId = userDetails.getMemberId();

        Member member = memberRepository.getReferenceById(ownerId);

        Store store = storeRepository.findByMember(member)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 가게가 존재하지 않습니다."));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 물품이 존재하지 않습니다."));

        if(!store.getStoreName().matches(item.getStore().getStoreName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 가게의 물품이 아닙니다.");
        }

        itemRepository.delete(item);

    }

    @Transactional(readOnly = true)
    public Page<SearchByWhiskeyResponseDto> searchByWhiskeyName(String whiskeyName, Pageable pageable) {

        //위스키 이름이 존재하는 지 확인 및 위스키 가져오기
        Whiskey whiskey = whiskeyRepository.findByWhiskeyName(whiskeyName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 위스키가 존재하지 않습니다."));


        //가져온 위스키로 등록된 상품 페이지 네이션
        Page<Item> itemPage = itemRepository.findAllByWhiskey(whiskey, pageable);

        //Dto로 변경
        List<SearchByWhiskeyResponseDto> dtoList =  itemPage.getContent().stream()
                .map(SearchByWhiskeyResponseDto::new)
                .toList();

        return new PageImpl<>( dtoList, pageable, itemPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Slice<SearchByWhiskeyResponseDto> searchByWhiskeyType(String whiskeyType, Pageable pageable) {

        if(!whiskeyRepository.existsByWhiskeyType(whiskeyType)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 범주의 위스키를 찾을 수 없습니다.");
        }

        Slice<Item> itemSlice = itemRepository.findAllByWhiskey_WhiskeyType(whiskeyType, pageable);

        return itemSlice.map(SearchByWhiskeyResponseDto::new);
    }

    @Transactional(readOnly = true)
    public Slice<SimpleStoreResponseDto> readForOrder(Long whiskeyId, Pageable pageable) {

        Whiskey whiskey = whiskeyRepository.findById(whiskeyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 위스키를 찾을 수 없습니다."));

        Slice<Item> itemSlice = itemRepository.findAllByWhiskey(whiskey, pageable);

        return itemSlice.map(SimpleStoreResponseDto::new);
    }
}
