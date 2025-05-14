package com.rauldetesan.price_comparator.services;

import com.rauldetesan.price_comparator.domain.Discount;
import com.rauldetesan.price_comparator.domain.Product;
import com.rauldetesan.price_comparator.domain.Store;
import com.rauldetesan.price_comparator.dtos.DiscountDTO;
import com.rauldetesan.price_comparator.dtos.DiscountResponseDTO;
import com.rauldetesan.price_comparator.exceptions.ResourceNotFoundException;
import com.rauldetesan.price_comparator.repositories.DiscountRepository;
import com.rauldetesan.price_comparator.repositories.ProductRepository;
import com.rauldetesan.price_comparator.repositories.StoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DiscountService {
    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    @Autowired
    public DiscountService(DiscountRepository discountRepository, ProductRepository productRepository, StoreRepository storeRepository) {
        this.discountRepository = discountRepository;
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
    }

    public DiscountResponseDTO findDiscountById(Long id){
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discount with id " + id + " does not exist"));

        return entityToDTO(discount);
    }

    private DiscountResponseDTO entityToDTO(Discount discount){
        DiscountResponseDTO dto = new DiscountResponseDTO();

        dto.setId(discount.getId());
        dto.setPercentage(discount.getPercentage());
        dto.setFromDate(discount.getFromDate());
        dto.setToDate(discount.getToDate());
        dto.setProductId(discount.getProduct().getId());
        dto.setStoreId(discount.getStore().getId());

        return dto;
    }

    public List<Discount> findAllDiscounts(){
        return discountRepository.findAll();
    }

    public void addDiscount(DiscountDTO dto){
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Store store = storeRepository.findById(dto.getStoreId())
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));

        Discount discount = new Discount();
        discount.setId(dto.getId());
        discount.setProduct(product);
        discount.setStore(store);
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

    @Transactional
    public void updateDiscountById(Long id,
                                   Product product,
                                   Store store,
                                   LocalDate fromDate,
                                   LocalDate toDate,
                                   double percentage
                                   ){
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discount with id " + id + " does not exist"));

        productRepository.findById(product.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " and name " + product.getName() + " does not exist"));

        storeRepository.findById(store.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Store with id " + id + " and name " +  store.getName() + " does not exist"));

        if(percentage>=0 && percentage<=100){
            discount.setProduct(product);
            discount.setStore(store);
            discount.setFromDate(fromDate);
            discount.setToDate(toDate);
            discount.setPercentage(percentage);
        }

        discountRepository.save(discount);


    }

}
