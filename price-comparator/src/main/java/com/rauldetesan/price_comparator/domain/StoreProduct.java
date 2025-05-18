package com.rauldetesan.price_comparator.domain;

import com.rauldetesan.price_comparator.enums.Unit;
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

    @JoinColumn(name="product_id")
    private String productId;

    @Column(name="product_name", nullable = false)
    private String name;

    @Column(name="category_name", nullable = false)
    private String category;

    @Column(nullable = false)
    private String brand;

    @Column(name="package_quantity", nullable = false)
    private double quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "package_unit")
    private Unit unit;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String currency;



    @Column(name="last_updated")
    private LocalDateTime lastUpdated = LocalDateTime.now();

    public StoreProduct() {
    }


    public StoreProduct(Long id, Store store, String product, String name, String category,
                        String brand, double quantity, Unit unit, BigDecimal price,
                        String currency, LocalDateTime lastUpdated) {
        this.id = id;
        this.store = store;
        this.productId = product;
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
        this.currency = currency;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "StoreProduct{" +
                "id=" + id +
                ", store=" + store +
                ", product=" + productId +
                ", price=" + price +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
