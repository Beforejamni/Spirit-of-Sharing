package com.finalproject.sos.domain.store.storeaddress.repository;


import com.finalproject.sos.domain.store.entity.Store;
import com.finalproject.sos.domain.store.storeaddress.entity.StoreAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreAddressRepository extends JpaRepository<StoreAddress, Long> {

    @Query("select sa from StoreAddress sa where sa.store = :store")
    Optional<StoreAddress> findByStore(@Param("store") Store store);
}
