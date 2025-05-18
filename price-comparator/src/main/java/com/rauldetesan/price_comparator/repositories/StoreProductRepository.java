package com.rauldetesan.price_comparator.repositories;

import com.rauldetesan.price_comparator.domain.StoreProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreProductRepository extends JpaRepository<StoreProduct, Long> {

    @Query(
            """
           SELECT sp FROM StoreProduct sp
           WHERE sp.store.id = :storeId
           AND sp.productId = :productId
           ORDER BY sp.lastUpdated DESC
            LIMIT 1
            """
    )
    Optional<StoreProduct> findLatestByStoreNameAndProductId(@Param("storeId") Long storeId,
                                               @Param("productId") String productId);

}
