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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {

    @Value("classpath:data/*.csv")
    private Resource[] csvFiles;

    private final StoreRepository storeRepository;
    private final DiscountRepository discountRepository;
    private final StoreProductRepository storeProductRepository;

    // We inject the repositories
    public DataLoader(StoreRepository storeRepository,
                      DiscountRepository discountRepository, StoreProductRepository storeProductRepository) {
        this.storeRepository = storeRepository;
        this.discountRepository = discountRepository;
        this.storeProductRepository = storeProductRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // Firstly, we are loading the stores because we need them for loading the store_products table
        for(Resource resource : csvFiles) {
            String filename = resource.getFilename();

            // We extract the store name from the csv file name
            String storeName = getStoreNameFromFileName(filename);

            // We create a new store if it doesn't exist already
            Store store = storeRepository.findByNameIgnoreCase(storeName)
                    .orElseGet(() -> new Store(null, storeName));

            storeRepository.save(store);
        }

        // After the stores are loaded, we go through all the data files again, now for populating the store_products tables
        for(Resource resource:csvFiles){
            String filename = resource.getFilename();

            // We extract the content of every csv file
            String content = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            // We map the content to a list of strings for our loadProductsFromFiles method
            List<String> lines = Arrays.stream(content.split("\n"))
                    .filter(line -> !line.isEmpty())
                    .toList();

            if(!filename.contains("discounts"))loadProductsFromFiles(filename, lines);
        }

        // After the stores and products are loaded, we can start loading the discounts
        for(Resource resource:csvFiles){
            String filename = resource.getFilename();

            // We extract the content of every csv file

            String content = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            // We map the content to a list of strings for our loadProductsFromFiles method
            List<String> lines = Arrays.stream(content.split("\n"))
                    .filter(line -> !line.isEmpty())
                    .toList();

            // If the file contains "discounts", we call loadDiscountsFromFiles to load the data
            if(filename.contains("discounts"))loadDiscountsFromFiles(filename, lines);
        }
    }


    /**
     * Populates the store_products table from the csv files (used for files with no "discounts" in their name)
     *
     * This method goes through every line of a file, extracts every field and populates the store_products table with data from the csv files.
     * At this point, the store table is populated with the stores so there will be no problem assigning products to stores.
     *
     * @param filename The name of the file from which we extract the data
     * @param lines List of every line of content from the file
     * @throws ResourceNotFoundException if the store is not present at all in the database
     *
     */

    private void loadProductsFromFiles(String filename, List<String> lines){

        String storeName = getStoreNameFromFileName(filename);
        String date = getDateFromFileName(filename);
        LocalDate localDate = LocalDate.parse(date);

        Store store = storeRepository.findByNameIgnoreCase(storeName)
                .orElseThrow(() -> new ResourceNotFoundException("Store not existent"));

        for(int i=1; i < lines.size(); i++){
            String line = lines.get(i);
            String[] parts = line.split(";");

            // We extract every field from every line of the csv file
            String productId = parts[0].trim();
            String name = parts[1].trim();
            String category = parts[2].trim();
            String brand = parts[3].trim();
            double quantity = Double.parseDouble(parts[4].trim());
            Unit unit = Unit.convertInput(parts[5].trim());
            double price = Double.parseDouble(parts[6].trim());
            String currency = parts[7].trim();

            // We build the storeProduct with the extracted data, and we save it to the database
            StoreProduct storeProduct = new StoreProduct(null, store, productId, name, category, brand, quantity
                    , unit, BigDecimal.valueOf(price)
                    , currency, localDate.atStartOfDay());
            storeProductRepository.save(storeProduct);
        }
    }

    /**
     * Populates the discounts table with the data from the discounts csv files. (used for files with "discounts" in their name)
     *
     * This method goes through every line of a discount file, extracts the relevant data and populates the discounts table
     *
     * @param filename The name of the file from which we extract the data
     * @param lines List of every line of content from the file
     * @throws ResourceNotFoundException if the store we are trying to add discounts to is not present at all in the database
     *
     */


    public void loadDiscountsFromFiles(String filename, List<String> lines){
        String storeName = getStoreNameFromFileName(filename);

        Store store = storeRepository.findByNameIgnoreCase(storeName)
                .orElseThrow(() -> new ResourceNotFoundException("Store not existent"));


        for(int i=1;i<lines.size();i++){
            String line = lines.get(i);
            String[] parts = line.split(";");

            // We need these fields for the discount table
            String productId = parts[0].trim();
            LocalDate fromDate = LocalDate.parse(parts[6].trim());
            LocalDate toDate = LocalDate.parse((parts[7].trim()));
            double percentage = Double.parseDouble(parts[8].trim());

            // We search the StoreProduct table for the last apparition of the product bt product id in the store by store id
            Optional<StoreProduct> storeProduct =
                    storeProductRepository.findLatestByStoreNameAndProductId(store.getId(), productId);

            Discount discount;

            // If the product is present in the database, we link the discount to it
            // If the product is not present in the database, we still add the discount but with no link to a product
            if(storeProduct.isPresent()){
                StoreProduct product = storeProduct.get();
                discount = new Discount(null, product, productId,
                        fromDate, toDate, percentage);
            }else{
                discount = new Discount(null, null, productId,
                        fromDate, toDate, percentage);
            }

            discountRepository.save(discount);
        }

    }


    /*
        These methods extract the Store Name and Date from the name of the csv files

        Example: from the file profi_2025-05-20.csv
        - getStoreNameFromFileName returns the String "profi"
        - getDateFromFileName returns 2025-05-20

     */

    private String getStoreNameFromFileName(String filename){
        return filename.split("_")[0].toLowerCase();
    }

    private String getDateFromFileName(String filename){
        return filename.split("_")[1].split("\\.")[0].toLowerCase();
    }
}
