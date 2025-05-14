package com.rauldetesan.price_comparator.repositories;

import com.rauldetesan.price_comparator.domain.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
