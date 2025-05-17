package com.rauldetesan.price_comparator.dataloader;

import com.rauldetesan.price_comparator.domain.Discount;
import com.rauldetesan.price_comparator.domain.Product;
import com.rauldetesan.price_comparator.domain.Store;
import com.rauldetesan.price_comparator.domain.StoreProduct;
import com.rauldetesan.price_comparator.enums.Unit;
import com.rauldetesan.price_comparator.exceptions.ResourceNotFoundException;
import com.rauldetesan.price_comparator.repositories.DiscountRepository;
import com.rauldetesan.price_comparator.repositories.ProductRepository;
import com.rauldetesan.price_comparator.repositories.StoreProductRepository;
import com.rauldetesan.price_comparator.repositories.StoreRepository;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class DataLoader implements CommandLineRunner {

    @Value("classpath:data/*.csv")
    private Resource[] csvFiles;

    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;
    private final StoreProductRepository storeProductRepository;

    // we inject the repositories
    public DataLoader(StoreRepository storeRepository, ProductRepository productRepository,
                      DiscountRepository discountRepository, StoreProductRepository storeProductRepository) {
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
        this.discountRepository = discountRepository;
        this.storeProductRepository = storeProductRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // LOAD STORES
        for(Resource resource : csvFiles) {
            String filename = resource.getFilename();

            String storeName = getStoreNameFromFileName(filename);

            Store store = storeRepository.findByNameIgnoreCase(storeName)
                    .orElseGet(() -> new Store(null, storeName));

            storeRepository.save(store);
        }

        // LOAD PRODUCTS
        for(Resource resource:csvFiles){
            String filename = resource.getFilename();

            String content = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            List<String> lines = Arrays.stream(content.split("\n"))
                    .filter(line -> !line.isEmpty())
                    .toList();

            loadProductsFromFiles(filename, lines);
        }
    }


    private void loadProductsFromFiles(String filename, List<String> lines){

        String storeName = getStoreNameFromFileName(filename);
        String date = getDateFromFileName(filename);
        Store store = storeRepository.findByNameIgnoreCase(storeName)
                .orElseThrow(() -> new ResourceNotFoundException("Store not existent"));

        for(int i=1; i < lines.size(); i++){
            String line = lines.get(i);
            String[] parts = line.split(";");

//            if(parts.length != 8){
//                System.err.println("Skip malformed line: " + line);
//                continue;
//            }

            Long storeId = store.getId();

            String productId = parts[0].trim();
            String name = parts[1].trim();
            String category = parts[2].trim();
            String brand = parts[3].trim();
            double quantity = Double.parseDouble(parts[4].trim());
            Unit unit = Unit.convertInput(parts[5].trim());
            double price = Double.parseDouble(parts[6].trim());
            String currency = parts[7].trim();

            Product product = new Product(productId, name, category, brand, quantity, unit, price, currency, store);
            productRepository.save(product);

            StoreProduct storeProduct = new StoreProduct(null, store, product, BigDecimal.valueOf(price), LocalDate.parse(date).atStartOfDay());
            storeProductRepository.save(storeProduct);
        }
    }

    private String getStoreNameFromFileName(String filename){
        return filename.split("_")[0].toLowerCase();
    }

    private String getDateFromFileName(String filename){
        return filename.split("_")[1].split("\\.")[0].toLowerCase();
    }
}
