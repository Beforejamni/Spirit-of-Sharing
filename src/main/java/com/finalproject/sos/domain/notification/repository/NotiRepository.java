package com.finalproject.sos.domain.notification.repository;

import com.finalproject.sos.domain.item.entity.Item;
import com.finalproject.sos.domain.member.entity.Member;
import com.finalproject.sos.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotiRepository extends JpaRepository<Notification, Long> {
    boolean existsByMemberAndItem(Member member, Item item);

    Notification findByMemberAndItem(Member member, Item item);
}
