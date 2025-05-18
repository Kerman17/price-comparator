package com.rauldetesan.price_comparator.repositories;

import com.rauldetesan.price_comparator.domain.StoreProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreProductRepository extends JpaRepository<StoreProduct, Long> {


}
