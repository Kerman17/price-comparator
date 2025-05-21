package com.rauldetesan.price_comparator.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name="user_email")
    private String email;

    @Column(name="user_password_hash")
    private String password;

    @Column(name="user_name")
    private String name;

    @OneToMany(mappedBy = "user", cascade=CascadeType.ALL, orphanRemoval = true)
    List<PriceAlert> priceAlerts = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "user_notifications", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "notifications")
    private List<String> notifications = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Basket basket;

    public void addNotification(String notification){
        notifications.add(notification);
    }

    public void deleteNotification(String notification){
        notifications.remove(notification);
    }

    public void addAlert(PriceAlert alert){
        priceAlerts.add(alert);
        alert.setUser(this);
    }

    public void removeAlert(PriceAlert alert){
        priceAlerts.remove(alert);
        alert.setUser(null);
    }

    public User() {
    }

    public User(Long id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User(Long id, String email, String password, String name, List<PriceAlert> priceAlerts) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.priceAlerts = priceAlerts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PriceAlert> getPriceAlerts() {
        return priceAlerts;
    }

    public void setPriceAlerts(List<PriceAlert> priceAlerts) {
        this.priceAlerts = priceAlerts;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }
}
