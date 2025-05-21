package com.rauldetesan.price_comparator.dtos.BasketDTOS;

import com.rauldetesan.price_comparator.dtos.BasketItemDTOS.BasketItemDTO;
import com.rauldetesan.price_comparator.dtos.BasketItemDTOS.BasketItemResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public class BasketResponseDTO {

    private Long id;
    private Long userId;
    private LocalDateTime createdAt = LocalDateTime.now();
    private List<BasketItemDTO> items;

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<BasketItemDTO> getItems() {
        return items;
    }

    public void setItems(List<BasketItemDTO> items) {
        this.items = items;
    }
}
