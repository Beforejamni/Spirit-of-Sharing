package com.finalproject.sos.domain.whiskey.repository;


import com.finalproject.sos.domain.whiskey.entity.Whiskey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WhiskeyRepository extends JpaRepository<Whiskey, Long> {
}
