package com.finalproject.sos.domain.auth.repository;

import com.finalproject.sos.domain.auth.entity.SignIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SignInRepository extends JpaRepository<SignIn, Long> {

    //아이디 존재 확인
    boolean existsByUsername(String username);

    //아이디로 Member와 SignIn 찾기
    @Query("select s from SignIn s join fetch s.member m where s.username = :username")
    Optional<SignIn> findWithMemberByUsername(@Param("username") String username);

    Optional<SignIn> findByUsername(String username);
}
