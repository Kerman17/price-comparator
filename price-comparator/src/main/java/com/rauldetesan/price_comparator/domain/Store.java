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

    public Store() {
    }

    public Store(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Store(Long id, String name, List<Discount> discounts, List<StoreProduct> storeProductList) {
        this.id = id;
        this.name = name;
        this.discounts = discounts;
        this.storeProductList = storeProductList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

    public List<StoreProduct> getStoreProductList() {
        return storeProductList;
    }

    public void setStoreProductList(List<StoreProduct> storeProductList) {
        this.storeProductList = storeProductList;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", discounts=" + discounts +
                ", storeProductList=" + storeProductList +
                '}';
    }
}
