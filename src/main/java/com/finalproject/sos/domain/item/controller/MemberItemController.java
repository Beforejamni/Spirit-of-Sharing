package com.finalproject.sos.domain.item.controller;


import com.finalproject.sos.domain.item.dto.response.ItemResponseDto;
import com.finalproject.sos.domain.item.dto.response.SearchByWhiskeyResponseDto;
import com.finalproject.sos.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member/item")
@RequiredArgsConstructor
public class MemberItemController {

    private final ItemService itemService;

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponseDto> readByMember(@PathVariable Long itemId) {

        return ResponseEntity.ok().body(itemService.readByMember(itemId));
    }


    //페이지네이션 사용
    @GetMapping("/whiskeyName")
    public ResponseEntity<Page<SearchByWhiskeyResponseDto>> searchByWhiskeyName(@RequestParam String whiskeyName,
                                                                @PageableDefault() Pageable pageable) {

        return ResponseEntity.ok().body(itemService.searchByWhiskeyName(whiskeyName, pageable));
    }


    //Slice 사용
    @GetMapping("/whiskeyType")
    public ResponseEntity<Slice<SearchByWhiskeyResponseDto>> searchByWhiskeyType(@RequestParam String whiskeyType,
                                                                                 @PageableDefault() Pageable pageable){

        return ResponseEntity.ok().body(itemService.searchByWhiskeyType(whiskeyType, pageable));
    }
}
