package com.rauldetesan.price_comparator.controllers;

import com.rauldetesan.price_comparator.domain.Discount;
import com.rauldetesan.price_comparator.domain.Product;
import com.rauldetesan.price_comparator.domain.Store;
import com.rauldetesan.price_comparator.dtos.DiscountDTO;
import com.rauldetesan.price_comparator.dtos.DiscountResponseDTO;
import com.rauldetesan.price_comparator.dtos.DiscountWithProductDTO;
import com.rauldetesan.price_comparator.services.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
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
    public List<DiscountResponseDTO> findAllDiscounts(){
        return discountService.findAllDiscounts();
    }

    @GetMapping("best")
    public ResponseEntity<List<DiscountWithProductDTO>> findTopDiscounts(
            @RequestParam(defaultValue = "10") int limit){
        List<DiscountWithProductDTO> discounts = discountService.findTopDiscounts(limit);
        return ResponseEntity.ok(discounts);
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

    @PutMapping("{discountId}/toDate")
    public void updateDiscountToDate(@PathVariable Long discountId,
                                     @RequestParam LocalDate toDate){
        discountService.updateDiscountToDate(discountId, toDate);
    }

    @PutMapping("{discountId}/percentage")
    public void updateDiscountPercentage(@PathVariable Long discountId,
                                         @RequestParam double percentage){

        discountService.updateDiscountPercentage(discountId, percentage);

    }


}
