package com.rauldetesan.price_comparator.repositories;

import com.rauldetesan.price_comparator.domain.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long> {

}
