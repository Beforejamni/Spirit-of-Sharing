package com.finalproject.sos.domain.store.repository;

import com.finalproject.sos.domain.member.entity.Member;
import com.finalproject.sos.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Store findByMember(Member member);
}
