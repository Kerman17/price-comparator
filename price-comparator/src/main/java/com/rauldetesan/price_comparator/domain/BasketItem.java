package com.rauldetesan.price_comparator.domain;

import jakarta.persistence.*;

@Entity
public class BasketItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private String preferredStore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    public BasketItem() {
    }

    public BasketItem(Long id, String productName, String preferredStore, User user) {
        this.id = id;
        this.productName = productName;
        this.preferredStore = preferredStore;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPreferredStore() {
        return preferredStore;
    }

    public void setPreferredStore(String preferredStore) {
        this.preferredStore = preferredStore;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
