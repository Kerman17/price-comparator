package com.rauldetesan.price_comparator.controllers;

import com.rauldetesan.price_comparator.dtos.BasketDTOS.BasketDTO;
import com.rauldetesan.price_comparator.dtos.BasketDTOS.BasketResponseDTO;
import com.rauldetesan.price_comparator.dtos.BasketDTOS.CheapestProductDTO;
import com.rauldetesan.price_comparator.services.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/baskets")
public class BasketController {

    private final BasketService basketService;

    @Autowired
    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping
    public List<BasketResponseDTO> getBaskets(){
        return basketService.getBaskets();
    }

    @PostMapping
    public void addBasket(@RequestBody BasketDTO basketDTO){
        basketService.addBasket(basketDTO);
    }

    @GetMapping("/{basketID}/cheapest-options")
    public List<CheapestProductDTO> getCheapestBasketOptions(
            @PathVariable Long basketID){
        return basketService.findCheapestProductsForBasket(basketID);
    }




}
