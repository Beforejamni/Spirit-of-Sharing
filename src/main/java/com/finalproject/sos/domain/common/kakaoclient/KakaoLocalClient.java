package com.finalproject.sos.domain.common.kakaoclient;


import com.finalproject.sos.config.KakaoFeignConfig;
import com.finalproject.sos.domain.common.kakaoclient.dto.KakaoAddressResponseDto;
import com.finalproject.sos.domain.common.kakaoclient.dto.KakaoKeywordResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoLocal",
url = "${kakao.local.base-url}",
configuration = KakaoFeignConfig.class)
public interface KakaoLocalClient {

    @GetMapping("/v2/local/search/address.json")
    KakaoAddressResponseDto searchAddress(@RequestParam("query") String query,
                                          @RequestParam(value = "size", defaultValue = "1") int size);

    @GetMapping("v2/local/search/keyword.json")
    KakaoKeywordResponse searchKeyword(@RequestParam("query") String keyword,
                                      @RequestParam("x") String x,
                                      @RequestParam("y") String y,
                                      @RequestParam("radius") int radius);
}
