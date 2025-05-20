package com.rauldetesan.price_comparator.dtos.DiscountDTOS;

import java.time.LocalDate;

public class BestDiscountDTO {
    private Long discountId;
    private int percentage;

    private LocalDate fromDate;
    private LocalDate toDate;

    private double priceWithoutDiscount;
    private Long storeId;
    private Long storeProductId;
    private String productName;

    public BestDiscountDTO() {
    }

    public BestDiscountDTO(Long discountId, int percentage, LocalDate fromDate,
                           LocalDate toDate, double priceWithoutDiscount, Long storeId,
                           Long storeProductId,
                            String productName) {
        this.discountId = discountId;
        this.percentage = percentage;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.priceWithoutDiscount = priceWithoutDiscount;
        this.storeId = storeId;
        this.storeProductId = storeProductId;
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
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

    public double getPriceWithoutDiscount() {
        return priceWithoutDiscount;
    }

    public void setPriceWithoutDiscount(double priceWithoutDiscount) {
        this.priceWithoutDiscount = priceWithoutDiscount;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getStoreProductId() {
        return storeProductId;
    }

    public void setStoreProductId(Long storeProductId) {
        this.storeProductId = storeProductId;
    }
}
