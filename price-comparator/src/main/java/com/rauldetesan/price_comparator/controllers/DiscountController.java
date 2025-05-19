package com.rauldetesan.price_comparator.controllers;


import com.rauldetesan.price_comparator.dtos.BestDiscountDTO;
import com.rauldetesan.price_comparator.dtos.DiscountDTO;
import com.rauldetesan.price_comparator.dtos.DiscountResponseDTO;
import com.rauldetesan.price_comparator.services.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @GetMapping("best")
//    public ResponseEntity<List<DiscountWithProductDTO>> findTopDiscounts(
//            @RequestParam(defaultValue = "10") int limit){
//        List<DiscountWithProductDTO> discounts = discountService.findTopDiscounts(limit);
//        return ResponseEntity.ok(discounts);
//    }

    @PostMapping
    public void addDiscount(@RequestBody DiscountDTO discountDTO){
        discountService.addDiscount(discountDTO);
    }

    @DeleteMapping("{discountId}")
    public void deleteDiscountById(@PathVariable Long discountId){
        discountService.deleteDiscountById(discountId);
    }

    @GetMapping("/best")
    public ResponseEntity<List<BestDiscountDTO>> findBestActiveDiscounts(
            @RequestParam(defaultValue = "10") int limit){
        return ResponseEntity.ok(discountService.findBestActiveDiscounts(limit));
    }

//    @PutMapping("{discountId}")
//    public void updateDiscountById(@PathVariable Long discountId,
//                                   @RequestParam LocalDate fromDate,
//                                   @RequestParam LocalDate toDate,
//                                   @RequestParam double percentage){
//        discountService.updateDiscountById(discountId,
//                fromDate,
//                toDate,
//                percentage);
//    }

//    @PutMapping("{discountId}/toDate")
//    public void updateDiscountToDate(@PathVariable Long discountId,
//                                     @RequestParam LocalDate toDate){
//        discountService.updateDiscountToDate(discountId, toDate);
//    }

//    @PutMapping("{discountId}/percentage")
//    public void updateDiscountPercentage(@PathVariable Long discountId,
//                                         @RequestParam double percentage){
//
//        discountService.updateDiscountPercentage(discountId, percentage);
//
//    }


}
