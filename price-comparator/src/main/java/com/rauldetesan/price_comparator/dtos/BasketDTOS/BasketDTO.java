package com.rauldetesan.price_comparator.dtos.BasketDTOS;

import com.rauldetesan.price_comparator.domain.BasketItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BasketDTO {

    private Long id;
    private Long userId;
    private List<BasketItem> items = new ArrayList<>();
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public void setItems(List<BasketItem> items) {
        this.items = items;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
