package com.finalproject.sos.domain.notification.entity;


import com.finalproject.sos.domain.common.entity.TimeStamped;
import com.finalproject.sos.domain.item.entity.Item;
import com.finalproject.sos.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;

@Getter
@Entity
@SoftDelete(columnName = "is_deleted")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notiId;

    @Column
    private boolean notiStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;



    public void updateNotiStatus() {
        this.notiStatus = !this.notiStatus;
    }

    public Notification(Member member, Item item) {
        this.member = member;
        this.item = item;
        this.notiStatus = true;
    }

}
