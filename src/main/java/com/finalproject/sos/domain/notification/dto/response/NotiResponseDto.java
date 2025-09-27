package com.finalproject.sos.domain.notification.dto.response;

import com.finalproject.sos.domain.notification.entity.Notification;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotiResponseDto {

    private String koreanName;

    private Long itemId;

    private boolean isNoti;

    public NotiResponseDto(Notification notification){
        this.koreanName = notification.getMember().getKoreanName();
        this.itemId = notification.getItem().getItemId();
        this.isNoti = notification.isNotiStatus();
    }
}
