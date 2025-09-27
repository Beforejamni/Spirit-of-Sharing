package com.finalproject.sos.domain.order.repository;


import com.finalproject.sos.domain.member.entity.Member;
import com.finalproject.sos.domain.order.entity.Order;
import com.finalproject.sos.domain.store.entity.Store;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Slice<Order> findAllByStore(Store store, Pageable pageable);

    Slice<Order> findAllByMember(Member member, Pageable pageable);
}
