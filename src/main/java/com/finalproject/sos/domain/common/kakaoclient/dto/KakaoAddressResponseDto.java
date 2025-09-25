package com.finalproject.sos.domain.common.kakaoclient.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoAddressResponseDto {

    @JsonProperty("documents")
    private List<Document> documentList;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Document{

        private String x;
        private String y;

        @JsonProperty("road_address")
        private RoadAddress roadAddress;
    }

    @Getter
    @Setter
    public static class RoadAddress{

        @JsonProperty("address_name")
        private  String AddressName;
    }
}
