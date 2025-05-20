package com.rauldetesan.price_comparator.dtos.StoreProductDTOS;

import com.rauldetesan.price_comparator.enums.Unit;

import java.time.LocalDateTime;

public class StoreProductRecommendationDTO {

    private String productName;
    private String brand;
    private String category;
    private double quantity;
    private Unit unit;
    private double price;
    private String currency;
    private String storeName;
    private double pricePerUnit;
    private LocalDateTime lastUpdated;

    public StoreProductRecommendationDTO() {
    }

    public StoreProductRecommendationDTO(String productName, String brand, String category, double quantity, Unit unit, double price, String currency, String storeName, double pricePerUnit, LocalDateTime lastUpdated) {
        this.productName = productName;
        this.brand = brand;
        this.category = category;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
        this.currency = currency;
        this.storeName = storeName;
        this.pricePerUnit = pricePerUnit;
        this.lastUpdated = lastUpdated;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
