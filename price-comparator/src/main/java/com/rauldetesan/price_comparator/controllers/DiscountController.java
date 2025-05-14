package com.rauldetesan.price_comparator.controllers;

import com.rauldetesan.price_comparator.domain.Discount;
import com.rauldetesan.price_comparator.domain.Product;
import com.rauldetesan.price_comparator.domain.Store;
import com.rauldetesan.price_comparator.dtos.DiscountDTO;
import com.rauldetesan.price_comparator.dtos.DiscountResponseDTO;
import com.rauldetesan.price_comparator.services.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/discounts")
public class DiscountController {

    private final DiscountService discountService;

    @Autowired
    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping("{discountId}")
    public DiscountResponseDTO findDiscountById(@PathVariable Long discountId){
        return discountService.findDiscountById(discountId);
    }

    @GetMapping
    public List<Discount> findAllDiscounts(){
        return discountService.findAllDiscounts();
    }

    @PostMapping
    public void addDiscount(@RequestBody DiscountDTO discountDTO){
        discountService.addDiscount(discountDTO);
    }

    @DeleteMapping("{discountId}")
    public void deleteDiscountById(@PathVariable Long discountId){
        discountService.deleteDiscountById(discountId);
    }

    @PutMapping("{discountId}")
    public void updateDiscountById(@PathVariable Long discountId,
                                   @RequestParam Product product,
                                   @RequestParam Store store,
                                   @RequestParam LocalDate fromDate,
                                   @RequestParam LocalDate toDate,
                                   @RequestParam double percentage){
        discountService.updateDiscountById(discountId,
                product,
                store,
                fromDate,
                toDate,
                percentage);
    }


}
