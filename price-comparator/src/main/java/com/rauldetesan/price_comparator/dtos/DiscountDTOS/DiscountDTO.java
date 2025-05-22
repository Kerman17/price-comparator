package com.rauldetesan.price_comparator.dtos.DiscountDTOS;

import com.rauldetesan.price_comparator.domain.StoreProduct;

import java.time.LocalDate;

public class DiscountDTO {
    private Long id;
    private Long storeProductId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private double percentage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getStoreProductId() {
        return storeProductId;
    }

    public void setStoreProductId(Long storeProductId) {
        this.storeProductId = storeProductId;
    }
}
