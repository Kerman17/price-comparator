package com.rauldetesan.price_comparator.dtos.UserDTOS;

import com.rauldetesan.price_comparator.domain.PriceAlert;

import java.util.List;

public class UserResponseDTO {
    private Long id;
    private String email;
    //private String password;
    private String name;
    List<PriceAlert> priceAlerts;

    public UserResponseDTO() {

    }

    public UserResponseDTO(Long id, String email, String password, String name, List<PriceAlert> priceAlerts) {
        this.id = id;
        this.email = email;

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



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PriceAlert> getPriceAlerts(List<PriceAlert> priceAlerts) {
        return this.priceAlerts;
    }

    public void setPriceAlerts(List<PriceAlert> priceAlerts) {
        this.priceAlerts = priceAlerts;
    }
}
