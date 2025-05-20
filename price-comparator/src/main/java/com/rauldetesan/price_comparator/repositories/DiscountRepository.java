package com.rauldetesan.price_comparator.repositories;

import com.rauldetesan.price_comparator.domain.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    //Best active discounts

    @Query(value = """
    SELECT
    d.id,
    d.percentage,
    d.from_date,
    d.to_date,
    p.price as "Price without discount",
    p.store_id,
    p.id as "Store Product Id",
    p.product_name
    FROM discounts d
    JOIN store_products p on d.store_product_id = p.id
    WHERE CURRENT_DATE BETWEEN d.from_date AND d.to_date
    ORDER BY d.percentage DESC
    LIMIT :limit
""", nativeQuery = true)
    // We set the type to Object because JPA returns as Object
    // because multiple entities are called
    List<Object[]> findBestActiveDiscounts(@Param("limit") int limit);

    @Query(value = """
    SELECT
    d.id,
    d.from_date,
    d.to_date,
    d.percentage
    FROM discounts d
    WHERE d.from_date >= NOW() - interval '24 HOURS'
    ORDER BY d.from_date DESC
    
""", nativeQuery = true)
    List<Object[]> findDiscountsAddedInTheLast24H();


}
