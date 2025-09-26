package com.finalproject.sos.domain.item.repository;


import com.finalproject.sos.domain.item.entity.Item;
import com.finalproject.sos.domain.whiskey.entity.Whiskey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findAllByWhiskey(Whiskey whiskey, Pageable pageable);

    Slice<Item> findAllByWhiskey_WhiskeyName(String whiskeyWhiskeyName, Pageable pageable);

    Slice<Item> findAllByWhiskey_WhiskeyType(String whiskeyWhiskeyType, Pageable pageable);
}
