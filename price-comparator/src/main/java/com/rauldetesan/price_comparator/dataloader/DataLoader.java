package com.rauldetesan.price_comparator.dataloader;

import com.rauldetesan.price_comparator.domain.Discount;
import com.rauldetesan.price_comparator.domain.Store;
import com.rauldetesan.price_comparator.domain.StoreProduct;
import com.rauldetesan.price_comparator.enums.Unit;
import com.rauldetesan.price_comparator.exceptions.ResourceNotFoundException;
import com.rauldetesan.price_comparator.repositories.DiscountRepository;
import com.rauldetesan.price_comparator.repositories.StoreProductRepository;
import com.rauldetesan.price_comparator.repositories.StoreRepository;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Value("classpath:data/*.csv")
    private Resource[] csvFiles;

    private final StoreRepository storeRepository;
    private final DiscountRepository discountRepository;
    private final StoreProductRepository storeProductRepository;

    // we inject the repositories
    public DataLoader(StoreRepository storeRepository,
                      DiscountRepository discountRepository, StoreProductRepository storeProductRepository) {
        this.storeRepository = storeRepository;
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

            if(!filename.contains("discounts"))loadProductsFromFiles(filename, lines);
        }

//        for(Resource resource:csvFiles){
//            String filename = resource.getFilename();
//
//            String content = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
//
//            List<String> lines = Arrays.stream(content.split("\n"))
//                    .filter(line -> !line.isEmpty())
//                    .toList();
//
//            if(filename.contains("discounts"))loadDiscountsFromFiles(filename, lines);
//        }
    }


    private void loadProductsFromFiles(String filename, List<String> lines){

        String storeName = getStoreNameFromFileName(filename);
        String date = getDateFromFileName(filename);
        LocalDate localDate = LocalDate.parse(date);
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

            StoreProduct storeProduct = new StoreProduct(null, store, productId, name, category, brand, quantity
                    , unit, BigDecimal.valueOf(price)
                    , currency, localDate.atStartOfDay());
            storeProductRepository.save(storeProduct);
        }
    }

    public void loadDiscountsFromFiles(String filename, List<String> lines){
        String storeName = getStoreNameFromFileName(filename);

        Store store = storeRepository.findByNameIgnoreCase(storeName)
                .orElseThrow(() -> new ResourceNotFoundException("Store not existent"));

        Long storeId = store.getId();


        for(int i=1;i<lines.size();i++){
            String line = lines.get(i);
            String parts[] = line.split(";");

            String productId = parts[0].trim();
            LocalDate fromDate = LocalDate.parse(parts[6].trim());
            LocalDate toDate = LocalDate.parse((parts[7].trim()));
            double percentage = Double.parseDouble(parts[8].trim());


            Discount discount = new Discount(null, fromDate, toDate, percentage);
            discountRepository.save(discount);
        }

    }

    private String getStoreNameFromFileName(String filename){
        return filename.split("_")[0].toLowerCase();
    }

    private String getDateFromFileName(String filename){
        return filename.split("_")[1].split("\\.")[0].toLowerCase();
    }
}
