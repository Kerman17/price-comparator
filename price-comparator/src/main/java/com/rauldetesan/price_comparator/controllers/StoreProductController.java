package com.rauldetesan.price_comparator.controllers;

import com.rauldetesan.price_comparator.dtos.StoreProductDTO;
import com.rauldetesan.price_comparator.dtos.StoreProductResponseDTO;
import com.rauldetesan.price_comparator.services.StoreProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

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

    @GetMapping
    public List<StoreProductResponseDTO> findStoresProducts(){
        return storeProductService.findStoresProducts();
    }

    @DeleteMapping("{storeProductId}")
    public void deleteStoreProductById(@PathVariable Long storeProductId){
        storeProductService.deleteStoreProductById(storeProductId);
    }

    @PutMapping("{storeProductId}")
    public void updateStoreProductPrice(@PathVariable Long storeProductId,
                                        @RequestParam BigDecimal price){
        storeProductService.updateStoreProductPrice(storeProductId, price);
    }

}
