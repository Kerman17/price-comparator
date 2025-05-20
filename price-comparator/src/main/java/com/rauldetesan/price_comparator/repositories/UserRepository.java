package com.rauldetesan.price_comparator.repositories;

import com.rauldetesan.price_comparator.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
