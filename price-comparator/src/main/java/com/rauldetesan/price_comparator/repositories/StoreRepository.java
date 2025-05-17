package com.rauldetesan.price_comparator.repositories;

import com.rauldetesan.price_comparator.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByNameIgnoreCase(String storename);
}
