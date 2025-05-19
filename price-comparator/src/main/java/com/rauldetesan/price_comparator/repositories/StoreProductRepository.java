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

    /*
        This query searches for the last updated StoreProduct
        with the storeId and productId
     */
    @Query(
            """
           SELECT p FROM StoreProduct p
           WHERE p.store.id = :storeId
           AND p.productId = :productId
           ORDER BY p.lastUpdated DESC
            LIMIT 1
            """
    )
    Optional<StoreProduct> findLatestByStoreNameAndProductId(@Param("storeId") Long storeId,
                                               @Param("productId") String productId);


    @Query(value =
            """
    SELECT
        DATE(p.last_updated),
        p.price,
        p.product_name,
        p.brand,
        p.category_name,
        s.store_name
    FROM store_products p
        JOIN stores s ON s.store_id = p.store_id
    WHERE p.product_name =:productName
        AND(:storeName IS NULL OR s.store_name = :storeName)
        AND(:brand IS NULL OR p.brand = :brand)
        AND(:categoryName IS NULL OR p.category_name =:categoryName)
    ORDER BY s.store_name, DATE(p.last_updated) ASC
    
    """, nativeQuery = true)
    List<Object[]> findPriceHistoryByProductName(
            @Param("productName") String productName,
            @Param("storeName") String storeName,
            @Param("brand") String brand,
            @Param("categoryName") String categoryName
    );

}
