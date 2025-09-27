package com.finalproject.sos.domain.member.service;


import com.finalproject.sos.domain.common.custom.CustomUserDetails;
import com.finalproject.sos.domain.common.kakaoclient.KakaoLocalClient;
import com.finalproject.sos.domain.member.address.dto.response.UpdateAddress;
import com.finalproject.sos.domain.member.address.entity.MemberAddress;
import com.finalproject.sos.domain.member.address.repository.MemberAddressRepository;
import com.finalproject.sos.domain.member.entity.Member;
import com.finalproject.sos.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final KakaoLocalClient kakao;

    private final MemberRepository memberRepository;
    private final MemberAddressRepository memberAddressRepository;

    @Transactional
    public Map<String, String> updateMemberAddress(CustomUserDetails userDetails, UpdateAddress updateAddress) {

        Long meId = userDetails.getMemberId();

        Member member = memberRepository.findById(meId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."));

        MemberAddress memberAddress = memberAddressRepository.findByMember(member);

        var addressResponse = kakao.searchAddress(updateAddress.getAddressName(), 1);

        if (addressResponse.getDocumentList() == null || addressResponse.getDocumentList().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 주소를 찾을 수 없습니다.");
        }

        var first = addressResponse.getDocumentList().get(0);

        BigDecimal lon = new BigDecimal(first.getX());
        BigDecimal lat = new BigDecimal(first.getY());

        var keywordResponse = kakao.searchKeyword(updateAddress.getAddressName(), lon.toPlainString(), lat.toPlainString(), 50);

        String kakaoPlaceId = (keywordResponse.getDocumentList() != null || keywordResponse.getDocumentList().isEmpty())
                ? keywordResponse.getDocumentList().get(0).getKakaoId()
                : null;

        memberAddress.updateMemberAddress(memberAddress.getAddressName(), lon, lat, kakaoPlaceId);

        Map<String, String> responseMap = new HashMap<>();

        responseMap.put("변경된 주소", memberAddress.getAddressName());

        return responseMap;
    }
}
