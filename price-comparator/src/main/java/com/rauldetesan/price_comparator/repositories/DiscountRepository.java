package com.rauldetesan.price_comparator.repositories;

import com.rauldetesan.price_comparator.domain.Discount;
import com.rauldetesan.price_comparator.dtos.DiscountResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    //Best active discounts

    @Query(value = """
    SELECT d.*, p.product_name, p.brand
    FROM discounts d
    JOIN products p on d.product_id = p.product_id
    JOIN stores s on d.store_id = s.store_id
    WHERE CURRENT_DATE BETWEEN d.from_date AND d.to_date
    ORDER BY d.percentage DESC
    LIMIT :limit
""", nativeQuery = true)
    List<Discount> findTopDiscounts(@Param("limit") int limit);
}
