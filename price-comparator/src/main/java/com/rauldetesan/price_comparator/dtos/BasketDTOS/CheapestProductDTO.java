package com.rauldetesan.price_comparator.dtos.BasketDTOS;

import com.rauldetesan.price_comparator.enums.Unit;

import java.time.LocalDateTime;

public class CheapestProductDTO {
    private String productName;
    private String brand;
    private String storeName;
    private double price;
    private String currency;
    private double quantity;
    private Unit unit;
    private LocalDateTime lastUpdated;

    public CheapestProductDTO() {
    }

    public CheapestProductDTO(String productName, String brand, String storeName, double price, String currency, double quantity, Unit unit, LocalDateTime lastUpdated) {
        this.productName = productName;
        this.brand = brand;
        this.storeName = storeName;
        this.price = price;
        this.currency = currency;
        this.quantity = quantity;
        this.unit = unit;
        this.lastUpdated = lastUpdated;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
