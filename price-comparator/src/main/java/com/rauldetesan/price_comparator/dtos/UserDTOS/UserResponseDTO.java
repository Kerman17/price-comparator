package com.rauldetesan.price_comparator.dtos.UserDTOS;

import com.rauldetesan.price_comparator.domain.PriceAlert;
import com.rauldetesan.price_comparator.dtos.BasketDTOS.BasketDTO;
import com.rauldetesan.price_comparator.dtos.PriceAlertDTOS.PriceAlertDTO;

import java.util.List;

public class UserResponseDTO {
    private Long id;
    private String email;
    private String name;
    List<PriceAlertDTO> priceAlerts;
    List<String> notifications;
    private Long basketId;

    public UserResponseDTO() {

    }

    public UserResponseDTO(Long id, String email, String password, String name
            , List<PriceAlertDTO> priceAlerts, List<String> notifications) {
        this.id = id;
        this.email = email;

        this.name = name;
        this.priceAlerts = priceAlerts;
        this.notifications = notifications;
    }

    public UserResponseDTO(Long id, String email, String name, List<PriceAlertDTO> priceAlerts, List<String> notifications, Long basketId) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.priceAlerts = priceAlerts;
        this.notifications = notifications;
        this.basketId = basketId;
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

    public List<PriceAlertDTO> getPriceAlerts() {
        return priceAlerts;
    }

    public void setPriceAlerts(List<PriceAlertDTO> priceAlerts) {
        this.priceAlerts = priceAlerts;
    }

    public Long getBasketId() {
        return basketId;
    }

    public void setBasketId(Long basketId) {
        this.basketId = basketId;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }
}
