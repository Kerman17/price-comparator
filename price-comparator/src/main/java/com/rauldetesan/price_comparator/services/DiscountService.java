package com.rauldetesan.price_comparator.services;

import com.rauldetesan.price_comparator.domain.Discount;
import com.rauldetesan.price_comparator.domain.StoreProduct;
import com.rauldetesan.price_comparator.dtos.BestDiscountDTO;
import com.rauldetesan.price_comparator.dtos.DiscountDTO;
import com.rauldetesan.price_comparator.dtos.DiscountResponseDTO;
import com.rauldetesan.price_comparator.exceptions.ResourceNotFoundException;
import com.rauldetesan.price_comparator.repositories.DiscountRepository;
import com.rauldetesan.price_comparator.repositories.StoreProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;


@Service
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final StoreProductRepository storeProductRepository;

    @Autowired
    public DiscountService(DiscountRepository discountRepository, StoreProductRepository storeProductRepository) {
        this.discountRepository = discountRepository;
        this.storeProductRepository = storeProductRepository;
    }

    public DiscountResponseDTO findDiscountById(Long id){
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discount with id " + id + " does not exist"));

        return entityToDTO(discount);
    }

    public List<DiscountResponseDTO> findAllDiscounts(){
        List<Discount> discountList = discountRepository.findAll();

        return entityListToDtoList(discountList);
    }


    public void addDiscount(DiscountDTO dto) {
        StoreProduct storeProduct = storeProductRepository.findById(dto.getStoreProduct().getId())
                .orElseThrow(() -> new ResourceNotFoundException("StoreProduct with id " + dto.getStoreProduct().getId() + " not found"));

        Discount discount = new Discount();

        discount.setId(dto.getId());
        discount.setStoreProduct(storeProduct);
        discount.setProductId(storeProduct.getProductId());

        discount.setFromDate(dto.getFromDate());
        discount.setToDate(dto.getToDate());
        discount.setPercentage(dto.getPercentage());

        discountRepository.save(discount);
    }

    public void deleteDiscountById(Long id){

        // First we search if the discount exists
        discountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discount with id " + id + " does not exist"));

        discountRepository.deleteById(id);
    }

    public List<BestDiscountDTO> findBestActiveDiscounts(int limit) {

        List<Object[]> rows = discountRepository.findBestActiveDiscounts(limit);
        List<BestDiscountDTO> result = new ArrayList<>();

        for (Object[] row : rows) {
            Long discountId = ((Number) row[0]).longValue();
            int percentage = ((Number) row[1]).intValue();
            LocalDate fromDate = ((Date) row[2]).toLocalDate();
            LocalDate toDate = ((Date) row[3]).toLocalDate();
            double price = ((Number) row[4]).doubleValue();
            Long storeId = ((Number) row[5]).longValue();
            Long storeProductId = ((Number) row[6]).longValue();
            String productName = (String) row[7];

            result.add(new BestDiscountDTO(
                    discountId,
                    percentage,
                    fromDate,
                    toDate,
                    price,
                    storeId,
                    storeProductId,
                    productName
            ));
        }

        return result;
    }

    //
    // UPDATE endpoints BROKEN - TO DO
    //

//    @Transactional
//    public void updateDiscountById(Long id,
//                                   LocalDate fromDate,
//                                   LocalDate toDate,
//                                   double percentage
//                                   ){
//        Discount discount = discountRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Discount with id " + id + " does not exist"));
//
//
//        if(percentage>=0 && percentage<=100){
//            discount.setFromDate(fromDate);
//            discount.setToDate(toDate);
//            discount.setPercentage(percentage);
//        }
//
//        discountRepository.save(discount);
//
//    }
//
//    @Transactional
//    public void updateDiscountToDate(Long id,
//                                     LocalDate toDate){
//        Discount discount = discountRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Discount with id " + id + " does not exist"));
//
//        discount.setToDate(toDate);
//
//        discountRepository.save(discount);
//    }
//
//    @Transactional
//    public void updateDiscountPercentage(Long id,
//                                         double percentage){
//        Discount discount = discountRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Discount with id " + id + " does not exist"));
//
//        discount.setPercentage(percentage);
//
//        discountRepository.save(discount);
//    }

    public DiscountResponseDTO entityToDTO(Discount discount){
        DiscountResponseDTO dto = new DiscountResponseDTO();

        dto.setId(discount.getId());
        dto.setPercentage(discount.getPercentage());
        dto.setFromDate(discount.getFromDate());
        dto.setToDate(discount.getToDate());
        dto.setStoreProduct(discount.getStoreProduct());

        return dto;
    }

    public List<DiscountResponseDTO> entityListToDtoList(List<Discount> discountList){
        List<DiscountResponseDTO> dtoList = new ArrayList<>();

        for(Discount discount : discountList){
            DiscountResponseDTO dto = new DiscountResponseDTO();

            dto = entityToDTO(discount);

            dtoList.add(dto);
        }

        return dtoList;
    }


}
