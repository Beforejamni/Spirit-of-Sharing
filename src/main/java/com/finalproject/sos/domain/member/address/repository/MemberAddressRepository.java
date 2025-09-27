package com.finalproject.sos.domain.member.address.repository;


import com.finalproject.sos.domain.member.address.entity.MemberAddress;
import com.finalproject.sos.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberAddressRepository extends JpaRepository<MemberAddress, Long> {
    MemberAddress findByMember(Member member);
}
