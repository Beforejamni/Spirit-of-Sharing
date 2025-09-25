package com.finalproject.sos.domain.whiskey.service;


import com.finalproject.sos.domain.whiskey.dto.request.WhiskeyRequestDto;
import com.finalproject.sos.domain.whiskey.dto.response.WhiskeyResponseDto;
import com.finalproject.sos.domain.whiskey.entity.Whiskey;
import com.finalproject.sos.domain.whiskey.repository.WhiskeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class WhiskeyService {

    private final WhiskeyRepository whiskeyRepository;


    @Transactional
    public WhiskeyResponseDto saveWhiskey(WhiskeyRequestDto whiskeyRequestDto) {

        if(whiskeyRepository.existsByWhiskeyName(whiskeyRequestDto.getWhiskeyName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 위스키입니다.");
        }

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

    @Transactional
    public WhiskeyResponseDto updateWhiskey( Long whiskeyId, WhiskeyRequestDto whiskeyRequestDto) {

        Whiskey whiskey = whiskeyRepository.findById(whiskeyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 위스키를 찾을 수 없습니다."));

        whiskey.updateWhiskey(whiskeyRequestDto);

        return WhiskeyResponseDto.builder().whiskey(whiskey).build();

    }
}
