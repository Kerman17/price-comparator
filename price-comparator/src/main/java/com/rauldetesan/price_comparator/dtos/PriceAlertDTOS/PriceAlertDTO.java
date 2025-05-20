package com.rauldetesan.price_comparator.dtos.PriceAlertDTOS;

import com.rauldetesan.price_comparator.domain.User;

import java.math.BigDecimal;

public class PriceAlertDTO {
    private Long id;
    private String productName;
    private BigDecimal targetPrice;
    private Long userId;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
