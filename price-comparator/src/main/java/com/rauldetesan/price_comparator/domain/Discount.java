package com.rauldetesan.price_comparator.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "discounts")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "store_product_id")
    private StoreProduct storeProduct;

    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private LocalDate fromDate;

    @Column(nullable = false)
    private LocalDate toDate;

    @Column(nullable = false)
    private double percentage;

    @Column(nullable = false)
    LocalDateTime created_at = LocalDateTime.now();

    public Discount() {
    }

    public Discount(Long id, StoreProduct storeProduct, String productId, LocalDate fromDate, LocalDate toDate, double percentage) {
        this.id = id;
        this.storeProduct = storeProduct;
        this.productId = productId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.percentage = percentage;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StoreProduct getStoreProduct() {
        return storeProduct;
    }

    public void setStoreProduct(StoreProduct storeProduct) {
        this.storeProduct = storeProduct;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "id=" + id +
                ", storeProduct=" + storeProduct +
                ", productId='" + productId + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", percentage=" + percentage +
                '}';
    }
}
