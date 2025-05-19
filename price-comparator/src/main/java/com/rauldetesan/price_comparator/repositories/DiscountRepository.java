package com.rauldetesan.price_comparator.repositories;

import com.rauldetesan.price_comparator.domain.Discount;
import com.rauldetesan.price_comparator.dtos.DiscountResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    //Best active discounts

    @Query(value = """
    SELECT d.*, p.price as "Price without discount", p.store_id, p.id as "Store Product Id"
    FROM discounts d
    JOIN store_products p on d.store_product_id = p.id
    WHERE CURRENT_DATE BETWEEN d.from_date AND d.to_date
    ORDER BY d.percentage DESC
    LIMIT :limit
""", nativeQuery = true)
    // We set the type to Object because JPA returns as Object
    // because multiple entities are called
    List<Object[]> findBestActiveDiscounts(@Param("limit") int limit);


}
