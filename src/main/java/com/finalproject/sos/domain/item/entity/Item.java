package com.finalproject.sos.domain.item.entity;


import com.finalproject.sos.domain.common.entity.TimeStamped;
import com.finalproject.sos.domain.item.dto.request.ItemRequestDto;
import com.finalproject.sos.domain.order.entity.Order;
import com.finalproject.sos.domain.store.entity.Store;
import com.finalproject.sos.domain.whiskey.entity.Whiskey;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SoftDelete;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@Table(
        name = "item",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"whiskey_id", "store_id"})
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SoftDelete(columnName = "is_deleted")
public class Item extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column
    @Enumerated(EnumType.STRING)
    private SnackType snackType;

    @Column
    private String cocktail;

    @Column(nullable = false)
    private Integer itemCnt;

    @Column(precision = 10, scale = 2 , nullable = false)
    private BigDecimal itemPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "whiskey_id", nullable = false)
    private Whiskey whiskey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Order> orderList = new ArrayList<>();


    public Item(ItemRequestDto itemRequestDto, Whiskey whiskey, Store store){
        this.snackType = (itemRequestDto.getSnackType() != null) ? itemRequestDto.getSnackType() : null;
        this.cocktail = (itemRequestDto.getCocktail() != null) ? itemRequestDto.getCocktail() : null;
        this.itemCnt = itemRequestDto.getItemCnt();
        this.itemPrice = itemRequestDto.getItemPrice();
        this.whiskey = whiskey;
        this.store = store;
    }


    public void updateItem(ItemRequestDto itemRequestDto){
        this.snackType = (itemRequestDto.getSnackType() != null) ? itemRequestDto.getSnackType() : this.snackType;
        this.cocktail = (itemRequestDto.getCocktail() != null) ? itemRequestDto.getCocktail() : this.cocktail;
        this.itemCnt = (itemRequestDto.getItemCnt() != null) ? itemRequestDto.getItemCnt() : this.itemCnt;
        this.itemPrice = (itemRequestDto.getItemPrice() != null) ? itemRequestDto.getItemPrice() : this.itemPrice;
    }

    public void restItemCnt(int rest) {
        this.itemCnt = rest;
    }
}
