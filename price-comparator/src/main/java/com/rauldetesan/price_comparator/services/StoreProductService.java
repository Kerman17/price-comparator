package com.rauldetesan.price_comparator.services;

import com.rauldetesan.price_comparator.domain.Store;
import com.rauldetesan.price_comparator.domain.StoreProduct;
import com.rauldetesan.price_comparator.dtos.StoreProductDTOS.PriceHistoryDTO;
import com.rauldetesan.price_comparator.dtos.StoreProductDTOS.StoreProductDTO;
import com.rauldetesan.price_comparator.dtos.StoreProductDTOS.StoreProductRecommendationDTO;
import com.rauldetesan.price_comparator.dtos.StoreProductDTOS.StoreProductResponseDTO;
import com.rauldetesan.price_comparator.enums.Unit;
import com.rauldetesan.price_comparator.exceptions.ResourceNotFoundException;
import com.rauldetesan.price_comparator.repositories.StoreProductRepository;
import com.rauldetesan.price_comparator.repositories.StoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreProductService {
    private final StoreProductRepository storeProductRepository;
    private final StoreRepository storeRepository;

    @Autowired
    public StoreProductService(StoreProductRepository storeProductRepository, StoreRepository storeRepository) {
        this.storeProductRepository = storeProductRepository;
        this.storeRepository = storeRepository;
    }

    public void addStoreProduct(StoreProductDTO dto){

        Store store = storeRepository.findById(dto.getStoreId())
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));

        StoreProduct storeProduct = new StoreProduct();

        storeProduct.setId(dto.getId());
        storeProduct.setPrice(dto.getPrice());
        storeProduct.setStore(store);
        storeProduct.setProductId(dto.getProductId());

        storeProduct.setBrand(dto.getBrand());
        storeProduct.setName(dto.getName());
        storeProduct.setUnit(dto.getUnit());
        storeProduct.setQuantity(dto.getQuantity());

        storeProduct.setCategory(dto.getCategory());
        storeProduct.setCurrency(dto.getCurrency());

        storeProductRepository.save(storeProduct);
    }


    public StoreProductResponseDTO findStoreProductById(Long id){
        StoreProduct storeProduct = storeProductRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store-Product with id " + id + " does not exist"));

        return storeProductEntityToDTO(storeProduct);
    }

    public StoreProductResponseDTO storeProductEntityToDTO(StoreProduct storeProduct){
        StoreProductResponseDTO dto = new StoreProductResponseDTO();

        dto.setId(storeProduct.getId());
        dto.setProductId(storeProduct.getProductId());
        dto.setStoreId(storeProduct.getStore().getId());
        dto.setPrice(storeProduct.getPrice());


        return dto;
    }

    public List<StoreProductResponseDTO> findStoresProducts(){
        List<StoreProduct> storeProductList = storeProductRepository.findAll();

        return entityListToDtoListStoreProduct(storeProductList);
    }

    public List<StoreProductResponseDTO> entityListToDtoListStoreProduct
            (List<StoreProduct> storeProductList){

        List<StoreProductResponseDTO> dtoList = new ArrayList<>();

        for(StoreProduct storeProduct : storeProductList){
            StoreProductResponseDTO dto = new StoreProductResponseDTO();

            dto = storeProductEntityToDTO(storeProduct);

            dtoList.add(dto);
        }

        return dtoList;
    }

    public void deleteStoreProductById(Long id){
        storeProductRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store-Product with id " + id + " does not exist"));

        storeProductRepository.deleteById(id);
    }

    @Transactional
    public void updateStoreProductPrice(Long id,
                                        BigDecimal price){
        StoreProduct storeProduct =  storeProductRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store-Product with id " + id + " does not exist"));

        if(price.compareTo(BigDecimal.ZERO)>0){
            storeProduct.setPrice(price);
        }

        storeProductRepository.save(storeProduct);
    }

    public List<PriceHistoryDTO> findPriceHistoryByProductName(String productName,
                                                               String storeName,
                                                               String brand,
                                                               String categoryName) {
        List<Object[]> rows = storeProductRepository
                .findPriceHistoryByProductName(productName, storeName, brand, categoryName);

        return rows.stream().map(row -> {
            // Cast java.sql.Date to LocalDate
            LocalDate date = ((java.sql.Date) row[0]).toLocalDate();
            double price = ((Number) row[1]).doubleValue();
            String product = (String) row[2];
            String brandName = (String) row[3];
            String category = (String) row[4];
            String store = (String) row[5];

            return new PriceHistoryDTO(date, price, product, brandName, category, store);
        }).collect(Collectors.toList());
    }

    public List<StoreProductRecommendationDTO> findSameCategoryStoreProducts(String categoryName, String storeName){
        List<Object[]> results = storeProductRepository.findSameCategoryStoreProducts(categoryName, storeName);

        return results.stream().map(row ->{
            String productName = (String) row[0];
            String brand = (String) row[1];
            String category = (String) row[2];
            double quantity = ((Number) row[3]).doubleValue();
            String unitStr = (String) row[4];
            BigDecimal price = (BigDecimal) row[5];
            String currency = (String) row[6];
            LocalDateTime lastUpdated = ((Timestamp) row[7]).toLocalDateTime();
            String store = (String) row[8];
            double pricePerUnit = ((Number) row[9]).doubleValue();

            return new StoreProductRecommendationDTO(
                    productName,
                    brand,
                    category,
                    quantity,
                    Unit.valueOf(unitStr),
                    price.doubleValue(),
                    currency,
                    store,
                    // converting to string so we can format to 2 decimals, then back to double
                    Double.parseDouble(String.format("%.2f", pricePerUnit)) ,
                    lastUpdated
            );
                }).collect(Collectors.toList());
    }

}
