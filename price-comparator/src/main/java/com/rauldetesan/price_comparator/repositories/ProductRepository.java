package com.rauldetesan.price_comparator.repositories;

import com.rauldetesan.price_comparator.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}
