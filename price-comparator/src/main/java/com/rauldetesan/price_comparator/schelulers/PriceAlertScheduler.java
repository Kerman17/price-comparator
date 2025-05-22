package com.rauldetesan.price_comparator.schelulers;

import com.rauldetesan.price_comparator.domain.PriceAlert;
import com.rauldetesan.price_comparator.domain.StoreProduct;
import com.rauldetesan.price_comparator.domain.User;
import com.rauldetesan.price_comparator.repositories.PriceAlertRepository;
import com.rauldetesan.price_comparator.repositories.StoreProductRepository;
import com.rauldetesan.price_comparator.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Transactional
@Component
public class PriceAlertScheduler {

    private final PriceAlertRepository priceAlertRepository;
    private final StoreProductRepository storeProductRepository;
    private final UserRepository userRepository;

    @Autowired
    public PriceAlertScheduler(PriceAlertRepository priceAlertRepository, StoreProductRepository storeProductRepository, UserRepository userRepository) {
        this.priceAlertRepository = priceAlertRepository;
        this.storeProductRepository = storeProductRepository;
        this.userRepository = userRepository;
    }

    /**
     * Goes through all the active PriceAlerts and extracts the productName and targetPrice from every PriceAlert
     * Searches in the StoreProduct table all the products with the productName specified by the user in the PriceAlert
     * Goes through all the found products and compares the price with the targetPrice specified by the user in the PriceAlert
     * If the price is strictly lower than the targetPrice, the app sends a notification to the user regarding the cheaper product
     *
     */

    @Scheduled(fixedRate = 10_000)
    public void checkPriceAlerts(){

        // Fetch all the PriceAlerts
        List<PriceAlert> alerts = priceAlertRepository.findAll();

        for(PriceAlert alert : alerts){

            // Extract the productName and normalize it to lowercase and the targetPrice
            String productName = alert.getProductName().toLowerCase();
            BigDecimal targetPrice = alert.getTargetPrice();

            // Fetch all the existing products with name productName
            List<StoreProduct> products = storeProductRepository
                    .findByNameIgnoreCase(productName);

            System.out.println("FOUND MATCHING NUMBER OF PRODUCTS  " + products.size());


            for(StoreProduct product : products){

                // Compare every product with the same name targetPrice
                if(product.getPrice().compareTo(targetPrice)<0){
                    // If targetPrice is lower, we fetch the owner of the alert
                    User user = alert.getUser();

                    // Create the notification
                    String message = "Product " + product.getName() + " reached the set price of " + alert.getTargetPrice()
                            + " at the store " + product.getStore().getName();

                    System.out.println(message + "FOR USER " + " " + user.getName());

                    // If the user doesn't have the notification already, we add it to their notification list
                    if(!user.getNotifications().contains(message))
                        user.addNotification(message);


                    userRepository.save(user);

                    break;
                }
            }
        }
    }
}
