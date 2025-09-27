package com.finalproject.sos.domain.notification.repository;

import com.finalproject.sos.domain.item.entity.Item;
import com.finalproject.sos.domain.member.entity.Member;
import com.finalproject.sos.domain.notification.entity.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface NotiRepository extends JpaRepository<Notification, Long> {

    boolean existsByMemberAndItem(Member member, Item item);

    @Query("select i from Notification n join n.item i where n.member.memberId =:meId and n.notiStatus = true")
    Slice<Item> findItemByMemberId(Long meId, Pageable pageable);
}
