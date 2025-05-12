package com.rauldetesan.price_comparator.domain;

import com.rauldetesan.price_comparator.enums.Unit;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "stores")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Column(name = "store_name")
    private String name;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Discount> discounts;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<StoreProduct> storeProductList;

}
