package com.rauldetesan.price_comparator.controllers;

import com.rauldetesan.price_comparator.domain.Store;
import com.rauldetesan.price_comparator.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/stores")
public class StoreController {

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("{storeId}")
    public Store findStoreById(@PathVariable Long storeId){
        return storeService.findStoreById(storeId);
    }

    @PostMapping
    public void addStore(@RequestBody Store store){
        storeService.addStore(store);
    }
}
