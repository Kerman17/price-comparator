package com.rauldetesan.price_comparator.services;

import com.rauldetesan.price_comparator.domain.Product;
import com.rauldetesan.price_comparator.domain.Store;
import com.rauldetesan.price_comparator.domain.StoreProduct;
import com.rauldetesan.price_comparator.dtos.StoreProductDTO;
import com.rauldetesan.price_comparator.dtos.StoreProductResponseDTO;
import com.rauldetesan.price_comparator.exceptions.ResourceNotFoundException;
import com.rauldetesan.price_comparator.repositories.ProductRepository;
import com.rauldetesan.price_comparator.repositories.StoreProductRepository;
import com.rauldetesan.price_comparator.repositories.StoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class StoreProductService {
    private final StoreProductRepository storeProductRepository;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    @Autowired
    public StoreProductService(StoreProductRepository storeProductRepository, ProductRepository productRepository, StoreRepository storeRepository) {
        this.storeProductRepository = storeProductRepository;
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
    }

    public void addStoreProduct(StoreProductDTO dto){
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Store store = storeRepository.findById(dto.getStoreId())
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));

        StoreProduct storeProduct = new StoreProduct();

        storeProduct.setId(dto.getId());
        storeProduct.setPrice(dto.getPrice());
        storeProduct.setProduct(product);
        storeProduct.setStore(store);

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
        dto.setProductId(storeProduct.getProduct().getId());
        dto.setStoreId(storeProduct.getStore().getId());
        dto.setPrice(storeProduct.getPrice());
        dto.setLastUpdated(storeProduct.getLastUpdated());

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

}
