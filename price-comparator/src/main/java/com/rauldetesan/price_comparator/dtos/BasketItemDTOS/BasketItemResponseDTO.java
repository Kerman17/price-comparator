package com.rauldetesan.price_comparator.dtos.BasketItemDTOS;

public class BasketItemResponseDTO {

    private Long id;
    private String productName;
    private String preferredStore = "";


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


}
