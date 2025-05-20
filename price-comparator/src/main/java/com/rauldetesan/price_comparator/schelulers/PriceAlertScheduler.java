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

    @Scheduled(fixedRate = 600000) // every 10 mins
    public void checkPriceAlerts(){

        List<PriceAlert> alerts = priceAlertRepository.findAll();

        for(PriceAlert alert : alerts){

            String productName = alert.getProductName().toLowerCase();
            BigDecimal targetPrice = alert.getTargetPrice();

            List<StoreProduct> products = storeProductRepository
                    .findByNameIgnoreCase(productName);

            // System.out.println("NUMBER OF PRODUCTS  " + products.size());

            for(StoreProduct product : products){

                if(product.getPrice().compareTo(targetPrice)<=0){
                    User user = alert.getUser();

                    String message = "Product " + product.getName() + " reached the set price of " + alert.getTargetPrice()
                            + " at the store " + product.getStore().getName();

                    // System.out.println(message + "FOR USER " + user.getName());

                    if(!user.getNotifications().contains(message))
                        user.addNotification(message);


                    userRepository.save(user);

                    break;
                }
            }
        }
    }
}
