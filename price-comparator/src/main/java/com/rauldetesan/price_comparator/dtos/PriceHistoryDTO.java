package com.rauldetesan.price_comparator.dtos;

import java.time.LocalDate;

public class PriceHistoryDTO {

    private LocalDate date;
    private double price;
    private String productName;
    private String brand;
    private String categoryName;
    private String storeName;

    public PriceHistoryDTO() {
    }

    public PriceHistoryDTO(LocalDate date, double price, String productName, String brand, String categoryName, String storeName) {
        this.date = date;
        this.price = price;
        this.productName = productName;
        this.brand = brand;
        this.categoryName = categoryName;
        this.storeName = storeName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
