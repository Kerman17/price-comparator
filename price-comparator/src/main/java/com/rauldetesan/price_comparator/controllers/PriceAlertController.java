package com.rauldetesan.price_comparator.controllers;

import com.rauldetesan.price_comparator.domain.PriceAlert;
import com.rauldetesan.price_comparator.dtos.PriceAlertDTOS.PriceAlertDTO;
import com.rauldetesan.price_comparator.services.PriceAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/price-alerts")
public class PriceAlertController {

    private final PriceAlertService priceAlertService;

    @Autowired
    public PriceAlertController(PriceAlertService priceAlertService) {
        this.priceAlertService = priceAlertService;
    }

    @GetMapping
    public List<PriceAlert> findAllPriceAlerts(){
        return priceAlertService.getAllPriceAlerts();
    }

    @PostMapping
    public void addPriceAlert(@RequestBody PriceAlertDTO priceAlert){
        priceAlertService.addPriceAlert(priceAlert);
    }

}
