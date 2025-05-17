package com.rauldetesan.price_comparator.dataloader;

import com.rauldetesan.price_comparator.repositories.DiscountRepository;
import com.rauldetesan.price_comparator.repositories.ProductRepository;
import com.rauldetesan.price_comparator.repositories.StoreProductRepository;
import com.rauldetesan.price_comparator.repositories.StoreRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataLoader implements CommandLineRunner {

    @Value("classpath:data/*.csv")
    private Resource[] csvFiles;

    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;
    private final StoreProductRepository storeProductRepository;

    public DataLoader(StoreRepository storeRepository, ProductRepository productRepository,
                      DiscountRepository discountRepository, StoreProductRepository storeProductRepository) {
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
        this.discountRepository = discountRepository;
        this.storeProductRepository = storeProductRepository;
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
