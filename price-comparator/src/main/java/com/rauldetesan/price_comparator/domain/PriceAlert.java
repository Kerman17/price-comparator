package com.rauldetesan.price_comparator.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="price_alerts")
public class PriceAlert {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "price_alert_id")
    private Long id;

    @Column
    @JoinColumn(name = "product_name")
    private String productName;

    @Column
    private BigDecimal targetPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public PriceAlert() {
    }

    public PriceAlert(Long id, String productName, BigDecimal targetPrice) {
        this.id = id;
        this.productName = productName;
        this.targetPrice = targetPrice;
    }


    public PriceAlert(Long id, String productName, BigDecimal targetPrice, User user) {
        this.id = id;
        this.productName = productName;
        this.targetPrice = targetPrice;
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

    public BigDecimal getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(BigDecimal targetPrice) {
        this.targetPrice = targetPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
