package com.finalproject.sos.domain.whiskey.service;


import com.finalproject.sos.domain.whiskey.dto.request.WhiskeyRequestDto;
import com.finalproject.sos.domain.whiskey.dto.response.WhiskeyResponseDto;
import com.finalproject.sos.domain.whiskey.entity.Whiskey;
import com.finalproject.sos.domain.whiskey.repository.WhiskeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WhiskeyService {

    private final WhiskeyRepository whiskeyRepository;

    public WhiskeyResponseDto saveWhiskey(WhiskeyRequestDto whiskeyRequestDto) {

        Whiskey whiskey = Whiskey.builder()
                .whiskeyName(whiskeyRequestDto.getWhiskeyName())
                .whiskeyBrand(whiskeyRequestDto.getWhiskeyBrand())
                .whiskeyUrl(whiskeyRequestDto.getWhiskeyUrl())
                .whiskeyAbv(whiskeyRequestDto.getWhiskeyAbv())
                .whiskeyType(whiskeyRequestDto.getWhiskeyType())
                .whiskeyTaste(whiskeyRequestDto.getWhiskeyTaste())
                .whiskeyAge(whiskeyRequestDto.getWhiskeyAge())
                .build();

        whiskeyRepository.save(whiskey);

        return WhiskeyResponseDto.builder().whiskey(whiskey).build();
    }
}
