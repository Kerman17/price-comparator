package com.rauldetesan.price_comparator.controllers;

import com.rauldetesan.price_comparator.dtos.BasketItemDTOS.BasketItemDTO;
import com.rauldetesan.price_comparator.dtos.BasketItemDTOS.BasketItemResponseDTO;
import com.rauldetesan.price_comparator.services.BasketItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/basket-items")
public class BasketItemController {

    private final BasketItemService basketItemService;

    @Autowired
    public BasketItemController(BasketItemService basketItemService) {
        this.basketItemService = basketItemService;
    }

    @PostMapping
    public void addBasketItem(@RequestBody BasketItemDTO dto){
        basketItemService.addBasketItem(dto);
    }

    @GetMapping
    public List<BasketItemResponseDTO> getBasketItems(){
        return basketItemService.getBasketItems();
    }

    @DeleteMapping("{basketItemId}")
    public void deleteBasketItem(@PathVariable Long basketItemId){
        basketItemService.deleteBasketItem(basketItemId);
    }

}
