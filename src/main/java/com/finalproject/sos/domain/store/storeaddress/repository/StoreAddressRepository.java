package com.finalproject.sos.domain.store.storeaddress.repository;


import com.finalproject.sos.domain.store.storeaddress.entity.StoreAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreAddressRepository extends JpaRepository<StoreAddress, Long> {
}
