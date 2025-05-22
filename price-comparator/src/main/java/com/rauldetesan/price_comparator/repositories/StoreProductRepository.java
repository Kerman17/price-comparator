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
        This query searches for the last updated StoreProduct with the storeId and productId
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


    /**
     *
     * Returns price history of product with productName, filterable by store, brand, category
     * Returns date - price - productName - brand - categoryName - storeName
     *
     * If only productName is completed, the query returns the price changes of product across all the tracked stores
     * If storeName is completed too, the query returns the price changes of product across that store
     * For brand, the query returns the price changes of product with said brand
     * For categoryName, the query returns the price changes of product with said category name
     *
     * @param productName name of the product for which we are looking for the price history
     * @param storeName filter for store name
     * @param brand filter for brand
     * @param categoryName filter for category of the product
     *
     */

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


    /**
     * This query is used to return all products from the same category
     * updated in the last 7 days ordered by price_per_unit after normalization of unit.
     * Used to recommend better product substitutes to the user.
     *
     * @param categoryName is the required parameter
     * @param storeName can be added to filter by store
     *
     */
    @Query(value =
            """
    SELECT
            p.product_name,
            p.brand,
            p.category_name,
            p.package_quantity,
            p.package_unit,
            p.price,
            p.currency,
            p.last_updated,
            s.store_name,
        CASE
            WHEN p.package_unit = 'G' THEN p.price / (p.package_quantity / 1000)
            WHEN p.package_unit = 'ML' THEN p.price / (p.package_quantity / 1000)
            ELSE p.price / p.package_quantity
        END AS price_per_unit
    FROM store_products p
    JOIN stores s on p.store_id = s.store_id
    WHERE p.category_name = :categoryName
        AND p.price IS NOT NULL AND p.package_quantity IS NOT NULL
        AND (:storeName IS NULL OR s.store_name= :storeName)
        AND (p.last_updated >= NOW() - interval '7 days')
    ORDER BY price_per_unit ASC
    LIMIT 10
""", nativeQuery = true)
    List<Object[]> findSameCategoryStoreProducts(
            @Param("categoryName") String categoryName,
            @Param("storeName") String storeName
    );
    List<StoreProduct> findByNameIgnoreCase(String productName);

    /**
     *
     * This query returns the cheapest products with name as productName - used in BasketService as a helper method
     *
     * @param productName is the required parameter, represents the name of the product for which we search for the cheapest one
     * @param storeName is used to filter by the store
     *
     */

    @Query(value = """
SELECT
    p.product_name,
    p.brand,
    p.category_name,
    p.package_quantity,
    p.package_unit,
    p.price,
    p.currency,
    p.last_updated,
    s.store_name
FROM store_products p
JOIN stores s on p.store_id = s.store_id
WHERE p.product_name = :productName
    AND (:storeName IS NULL OR :storeName = s.store_name)
    AND (p.last_updated >= NOW() - interval '7 days')
ORDER BY p.price ASC
""", nativeQuery = true)
    List<Object[]> findCheapestByProductName(
            @Param("productName") String productName,
            @Param("storeName") String storeName);

}
