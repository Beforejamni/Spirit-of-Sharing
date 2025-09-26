package com.finalproject.sos.domain.order.entity;

import com.finalproject.sos.domain.common.entity.TimeStamped;
import com.finalproject.sos.domain.item.entity.Item;
import com.finalproject.sos.domain.member.entity.Member;
import com.finalproject.sos.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;

import java.math.BigDecimal;

@Getter
@Entity
@SoftDelete(columnName = "is_deleted")
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(length = 20)
    private String orderNum;

    @Column
    private Integer orderCnt;

    @Column
    private BigDecimal orderPrice;

    @Column
    private String paymentMethod;

    @Column
    private OrderStatus orderStatus;

    //장바구니 구현 안 함
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;


    public Order(String orderNum,Integer orderCnt, BigDecimal orderPrice, String paymentMethod, OrderStatus status, Member member, Item item){
        this.orderNum = orderNum;
        this.orderCnt = orderCnt;
        this.orderPrice = orderPrice;
        this.paymentMethod = paymentMethod;
        this.orderStatus = status;
        this.member = member;
        this.store = item.getStore();
        this.item = item;
    }

    public void cancelOrder(){
        this.orderStatus = OrderStatus.CANCELED_ORDER;
    }
}
