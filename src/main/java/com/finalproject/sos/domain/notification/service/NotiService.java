package com.finalproject.sos.domain.notification.service;


import com.finalproject.sos.domain.common.custom.CustomUserDetails;
import com.finalproject.sos.domain.item.entity.Item;
import com.finalproject.sos.domain.item.repository.ItemRepository;
import com.finalproject.sos.domain.member.entity.Member;
import com.finalproject.sos.domain.member.repository.MemberRepository;
import com.finalproject.sos.domain.notification.dto.response.NotiResponseDto;
import com.finalproject.sos.domain.notification.entity.Notification;
import com.finalproject.sos.domain.notification.repository.NotiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class NotiService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final NotiRepository notiRepository;

    @Transactional
    public NotiResponseDto saveNoti(CustomUserDetails userDetails, Long itemId) {

        Long meId = userDetails.getMemberId();

        Member member = memberRepository.findById(meId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 품목을 찾을 수 없습니다."));

        if(notiRepository.existsByMemberAndItem(member, item)) {
           Notification notification = notiRepository.findByMemberAndItem(member, item);

           notification.updateNotiStatus();

           return new NotiResponseDto(notification);
        }

        Notification notification = new Notification(member, item);

        notiRepository.save(notification);

        return new NotiResponseDto(notification);
    }
}
