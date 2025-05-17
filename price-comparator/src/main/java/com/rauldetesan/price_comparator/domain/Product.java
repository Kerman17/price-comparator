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

    public Product() {
    }

    public Product(String id, String name, String category, String brand, double quantity, Unit unit, double price, String currency) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
        this.currency = currency;
    }

    public Product(String id, String name, String category, String brand, double quantity, Unit unit, double price, String currency, Store store) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
        this.currency = currency;
        this.store = store;
    }

    public Product(String id, String name, String category, String brand, double quantity, Unit unit, double price, String currency, Store store, List<StoreProduct> storeProductList) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
        this.currency = currency;
        this.store = store;
        this.storeProductList = storeProductList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public List<StoreProduct> getStoreProductList() {
        return storeProductList;
    }

    public void setStoreProductList(List<StoreProduct> storeProductList) {
        this.storeProductList = storeProductList;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", quantity=" + quantity +
                ", unit=" + unit +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                ", store=" + store +
                ", storeProductList=" + storeProductList +
                '}';
    }
}
