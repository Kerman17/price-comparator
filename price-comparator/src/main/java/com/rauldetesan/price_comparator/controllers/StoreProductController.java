package com.rauldetesan.price_comparator.controllers;

import com.rauldetesan.price_comparator.dtos.StoreProductDTO;
import com.rauldetesan.price_comparator.dtos.StoreProductResponseDTO;
import com.rauldetesan.price_comparator.services.StoreProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/stores-products")
public class StoreProductController {

    private final StoreProductService storeProductService;

    @Autowired
    public StoreProductController(StoreProductService storeProductService) {
        this.storeProductService = storeProductService;
    }

    @PostMapping
    public void addStoreProduct(@RequestBody StoreProductDTO dto){
        storeProductService.addStoreProduct(dto);
    }

    @GetMapping("{storeProductId}")
    public StoreProductResponseDTO findStoreProductById(@PathVariable Long storeProductId){
        return storeProductService.findStoreProductById(storeProductId);
    }



}
