package com.finalproject.sos.domain.whiskey.repository;


import com.finalproject.sos.domain.whiskey.entity.Whiskey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WhiskeyRepository extends JpaRepository<Whiskey, Long> {

    boolean existsByWhiskeyName(String whiskeyName);

    Optional<Whiskey> findByWhiskeyName(String whiskeyName);
}
