package com.rauldetesan.price_comparator.repositories;

import com.rauldetesan.price_comparator.domain.PriceAlert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceAlertRepository extends JpaRepository<PriceAlert, Long> {
}
