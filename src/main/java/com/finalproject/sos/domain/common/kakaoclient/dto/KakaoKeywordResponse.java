package com.finalproject.sos.domain.common.kakaoclient.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoKeywordResponse {

    @JsonProperty("documents")
    private List<Document> documentList;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Document {

        @JsonProperty("id")
        private String kakaoId;

        @JsonProperty("place_name")
        private String placeName;

        @JsonProperty("address_name")
        private String x;

        @JsonProperty("road_address_name")
        private String y;
    }
}
