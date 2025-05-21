package com.rauldetesan.price_comparator.repositories;

import com.rauldetesan.price_comparator.domain.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {

}
