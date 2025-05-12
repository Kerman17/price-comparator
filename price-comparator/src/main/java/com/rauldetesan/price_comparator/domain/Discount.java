package com.rauldetesan.price_comparator.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "discounts")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name="product_id", nullable = false)
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name="store_id", nullable = false)
    private Store store;

    private LocalDate fromDate;

    private LocalDate toDate;

    private double percentage;

}
