package com.finalproject.sos.domain.whiskey.controller;


import com.finalproject.sos.domain.whiskey.dto.request.WhiskeyRequestDto;
import com.finalproject.sos.domain.whiskey.dto.response.WhiskeyResponseDto;
import com.finalproject.sos.domain.whiskey.service.WhiskeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/whiskey")
@RequiredArgsConstructor
public class WhiskeyController {

    private final WhiskeyService whiskeyService;

    @PostMapping()
    public ResponseEntity<WhiskeyResponseDto> saveWhiskey(@RequestBody WhiskeyRequestDto whiskeyRequestDto) {

        return new ResponseEntity<>( whiskeyService.saveWhiskey(whiskeyRequestDto), HttpStatus.CREATED);
    }


}
