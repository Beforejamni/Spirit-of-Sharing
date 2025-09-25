package com.finalproject.sos.domain.store.service;


import com.finalproject.sos.domain.common.custom.CustomUserDetails;
import com.finalproject.sos.domain.common.kakaoclient.KakaoLocalClient;
import com.finalproject.sos.domain.member.entity.Member;
import com.finalproject.sos.domain.member.repository.MemberRepository;
import com.finalproject.sos.domain.store.dto.request.ChangeStoreRequest;
import com.finalproject.sos.domain.store.dto.request.StoreRequestDto;
import com.finalproject.sos.domain.store.dto.response.SellerStoreResponse;
import com.finalproject.sos.domain.store.entity.Store;
import com.finalproject.sos.domain.store.entity.StoreStatus;
import com.finalproject.sos.domain.store.repository.StoreRepository;
import com.finalproject.sos.domain.store.storeaddress.entity.StoreAddress;
import com.finalproject.sos.domain.store.storeaddress.repository.StoreAddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    private final KakaoLocalClient kakao;

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final StoreAddressRepository storeAddressRepository;

    @Transactional
    public void saveStore(StoreRequestDto requestDto, CustomUserDetails userDetails) {

        Long meId = userDetails.getMemberId();


        // 권한은 Interceptor 혹은 Filter에서 처리
        Member member = memberRepository.findById(meId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저가 없습니다."));


        //값 출격 확인

        String raw = requestDto.getStoreAddress();
        log.debug("RAW addr='{}' (len={})", raw, raw == null ? 0 : raw.length());


        var addressResponse = kakao.searchAddress(requestDto.getStoreAddress(), 1);

        if(addressResponse.getDocumentList() == null || addressResponse.getDocumentList().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "주소를 찾을 수 없습니다.");
        }


        var first = addressResponse.getDocumentList().get(0);

        BigDecimal lon = new BigDecimal(first.getX());
        BigDecimal lat = new BigDecimal(first.getY());

        var keywordResponse = kakao.searchKeyword(requestDto.getStoreAddress(), lon.toPlainString(), lat.toPlainString(), 50);

        String kakaoPlaceId = (keywordResponse.getDocumentList() != null ||keywordResponse.getDocumentList().isEmpty())
                ? keywordResponse.getDocumentList().get(0).getKakaoId()
                : null;

        StoreAddress storeAddress = StoreAddress.builder()
                .storeAddress(requestDto.getStoreAddress())
                .lat(lat)
                .lon(lon)
                .kakaoPlaceId(kakaoPlaceId)
                .build();

        storeAddressRepository.save(storeAddress);

        Store store = Store.builder()
                .storeName(requestDto.getStoreName())
                .storeContact(requestDto.getStoreContact())
                .storeStatus(StoreStatus.PICKUP_DISABLE)
                .member(member)
                .storeAddress(storeAddress)
                .startTime(requestDto.getStartTime())
                .endTime(requestDto.getEndTime())
                .build();

        storeRepository.save(store);

    }


    @Transactional
    public SellerStoreResponse readByOwner(CustomUserDetails userDetails) {

        Long ownerId = userDetails.getMemberId();

        Member member = memberRepository.findById(ownerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND , "해당 사장님을 찾을 수 없습니다."));

        Store store = storeRepository.findByMember(member)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 가게를 찾을 수 없습니다."));

        StoreAddress storeAddress = storeAddressRepository.findByStore(store).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 주소을 찾을 수 없습니다."));

        String storeAddressName  = storeAddress.getStoreAddress();


        return new SellerStoreResponse(store.getStoreName(),
                                        storeAddressName,
                                        store.getStoreContact(),
                                        store.getStartTime(),
                                        store.getEndTime(),
                                        store.getStoreStatus());
    }

    @Transactional
    public SellerStoreResponse changeStore(CustomUserDetails userDetails, ChangeStoreRequest storeRequest) {

        Long ownerId = userDetails.getMemberId();

        Member member = memberRepository.findById(ownerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 사장님을 찾을 수 없습니다."));

        Store store = storeRepository.findByMember(member)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 가게를 찾을 수 없습니다."));

        store.changeStore(storeRequest.getStoreName(), storeRequest.getStoreContact(), storeRequest.getStartTime(),storeRequest.getEndTime());

        StoreAddress storeAddress = storeAddressRepository.findByStore(store)
                .orElseThrow(() -> new  ResponseStatusException(HttpStatus.NOT_FOUND, "해당 주소를 찾을 수 없습니다."));

        var addressResponse = kakao.searchAddress(storeRequest.getStoreAddress(), 1);

        var first = addressResponse.getDocumentList().get(0);

        BigDecimal lon = new BigDecimal(first.getX());
        BigDecimal lat = new BigDecimal(first.getY());

        var keywordResponse = kakao.searchKeyword(storeRequest.getStoreAddress(), lon.toPlainString(), lat.toPlainString(), 50);

        String kakaoPlaceId = (keywordResponse.getDocumentList() != null ||keywordResponse.getDocumentList().isEmpty())
                ? keywordResponse.getDocumentList().get(0).getKakaoId()
                : null;

        storeAddress.changeStoreAddress(storeRequest.getStoreAddress(), lat, lon, kakaoPlaceId);


        return new SellerStoreResponse(store.getStoreName(),
                storeAddress.getStoreAddress(),
                store.getStoreContact(),
                store.getStartTime(),
                store.getEndTime(),
                store.getStoreStatus());
    }


    @Transactional
    public SellerStoreResponse pickupableStore(CustomUserDetails userDetails) {

        Long ownerId = userDetails.getMemberId();

        Member member = memberRepository.getReferenceById(ownerId);

        Store store = storeRepository.findByMember(member)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 가게를 찾을 수 없습니다."));

       store.changeStoreStatus();

        return new SellerStoreResponse(store.getStoreName(),
                store.getStoreAddress().getStoreAddress(),
                store.getStoreContact(),
                store.getStartTime(),
                store.getEndTime(),
                store.getStoreStatus());
    }
}
