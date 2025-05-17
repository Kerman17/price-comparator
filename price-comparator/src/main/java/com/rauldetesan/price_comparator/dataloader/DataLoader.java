package com.rauldetesan.price_comparator.dataloader;

import com.rauldetesan.price_comparator.repositories.DiscountRepository;
import com.rauldetesan.price_comparator.repositories.ProductRepository;
import com.rauldetesan.price_comparator.repositories.StoreProductRepository;
import com.rauldetesan.price_comparator.repositories.StoreRepository;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


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
        for(Resource resource : csvFiles){
            String filename = resource.getFilename();

            System.out.println(getStoreNameFromFileName(filename));
        }
    }

    private String getStoreNameFromFileName(String filename){
        return filename.split("_")[0].toLowerCase();
    }
}
