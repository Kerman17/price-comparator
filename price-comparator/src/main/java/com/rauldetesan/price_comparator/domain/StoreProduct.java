package com.rauldetesan.price_comparator.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="store_products", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"store_id", "product_id"})
})
public class StoreProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="store_id")
    private Store store;

    @ManyToOne(optional = false)
    @JoinColumn(name="product_id")
    private Product product;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name="last_updated")
    private LocalDateTime lastUpdated;

    @PrePersist
    @PreUpdate
    public void updateTimestamp(){
        this.lastUpdated = LocalDateTime.now();
    }
}
