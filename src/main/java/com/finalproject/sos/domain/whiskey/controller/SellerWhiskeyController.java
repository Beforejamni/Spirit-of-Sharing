package com.finalproject.sos.domain.whiskey.controller;


import com.finalproject.sos.domain.whiskey.dto.response.WhiskeyResponseDto;
import com.finalproject.sos.domain.whiskey.service.WhiskeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller/whiskey")
@RequiredArgsConstructor
public class SellerWhiskeyController {


    private final WhiskeyService whiskeyService;

    @GetMapping()
    public ResponseEntity<WhiskeyResponseDto> readBySeller(@RequestParam String whiskeyName) {


        return ResponseEntity.ok().body(whiskeyService.readBySeller(whiskeyName));
    }
}
