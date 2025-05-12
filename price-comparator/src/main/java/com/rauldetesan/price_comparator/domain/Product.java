package com.rauldetesan.price_comparator.domain;

import com.rauldetesan.price_comparator.enums.Unit;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name="product_id")
    private String id;

    @Column(name="product_name", nullable = false)
    private String name;

    @Column(name="category_name", nullable = false)
    private String category;

    @Column(nullable = false)
    private String brand;

    @Column(name="package_quantity", nullable = false)
    private double quantity;

    @Column(name = "package_unit")
    @Enumerated(EnumType.STRING)
    private Unit unit;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String currency;

    @ManyToOne
    @JoinColumn(name="store_id")
    private Store store;

    @OneToMany(mappedBy = "product", cascade=CascadeType.ALL)
    private List<StoreProduct> storeProductList;

}
