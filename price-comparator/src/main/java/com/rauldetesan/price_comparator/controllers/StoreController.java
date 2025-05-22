package com.rauldetesan.price_comparator.controllers;

import com.rauldetesan.price_comparator.domain.Store;
import com.rauldetesan.price_comparator.dtos.StoreDTOS.StoreDTO;
import com.rauldetesan.price_comparator.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/stores")
public class StoreController {

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("{storeId}")
    public StoreDTO findStoreById(@PathVariable Long storeId){
        return storeService.findStoreById(storeId);
    }

    @GetMapping
    public List<StoreDTO> findAllStores(){
        return storeService.findAllStores();
    }

    @PostMapping
    public void addStore(@RequestBody Store store){
        storeService.addStore(store);
    }

    @DeleteMapping("{storeId}")
    public void deleteStoreById(@PathVariable Long storeId){
        storeService.deleteStoreById(storeId);
    }

    @PutMapping({"{storeId}"})
    public void updateStoreById(@PathVariable Long storeId,
                                @RequestParam String name){

        storeService.updateStoreById(storeId, name);
    }
}
