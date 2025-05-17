package com.rauldetesan.price_comparator.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="store_products")
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

    public StoreProduct() {
    }

    public StoreProduct(Long id, Store store, Product product, BigDecimal price) {
        this.id = id;
        this.store = store;
        this.product = product;
        this.price = price;
    }

    public StoreProduct(Long id, Store store, Product product, BigDecimal price, LocalDateTime lastUpdated) {
        this.id = id;
        this.store = store;
        this.product = product;
        this.price = price;
        this.lastUpdated = lastUpdated;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public String toString() {
        return "StoreProduct{" +
                "id=" + id +
                ", store=" + store +
                ", product=" + product +
                ", price=" + price +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
