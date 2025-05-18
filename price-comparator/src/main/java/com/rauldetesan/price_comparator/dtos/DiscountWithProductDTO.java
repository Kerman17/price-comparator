package com.rauldetesan.price_comparator.dtos;

import java.time.LocalDate;

public class DiscountWithProductDTO {
    private Long id;
    private String productId;
    private String productName;
    private String productBrand;
    private Long storeId;
    private String storeName;
    private LocalDate fromDate;

    private LocalDate toDate;
    private double percentage;



    public DiscountWithProductDTO() {
    }

    public DiscountWithProductDTO(Long id, String productId, Long storeId, LocalDate fromDate, LocalDate toDate, double percentage, String productName, String productBrand, String storeName) {
        this.id = id;
        this.productId = productId;
        this.storeId = storeId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.percentage = percentage;
        this.productName = productName;
        this.productBrand = productBrand;
        this.storeName = storeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
