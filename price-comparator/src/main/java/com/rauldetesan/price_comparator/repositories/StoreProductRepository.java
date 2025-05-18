package com.rauldetesan.price_comparator.repositories;

import com.rauldetesan.price_comparator.domain.Product;
import com.rauldetesan.price_comparator.domain.Store;
import com.rauldetesan.price_comparator.domain.StoreProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreProductRepository extends JpaRepository<StoreProduct, Long> {
    Optional<StoreProduct> findByStoreAndProduct(Store store, Product product);

    List<StoreProduct> findByStore(Store store);

}
