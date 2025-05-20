package com.rauldetesan.price_comparator.dtos.DiscountDTOS;

import java.time.LocalDate;

public class DiscountsInLast24hDTO {
    private Long discountId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private int percentage;

    public DiscountsInLast24hDTO() {
    }

    public DiscountsInLast24hDTO(Long discountId, LocalDate fromDate, LocalDate toDate, int percentage) {
        this.discountId = discountId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.percentage = percentage;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
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

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
