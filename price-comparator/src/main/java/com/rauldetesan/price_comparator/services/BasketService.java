package com.rauldetesan.price_comparator.services;

import com.rauldetesan.price_comparator.domain.Basket;
import com.rauldetesan.price_comparator.domain.BasketItem;
import com.rauldetesan.price_comparator.domain.User;
import com.rauldetesan.price_comparator.dtos.BasketDTOS.BasketDTO;
import com.rauldetesan.price_comparator.dtos.BasketDTOS.BasketResponseDTO;
import com.rauldetesan.price_comparator.dtos.BasketDTOS.CheapestProductDTO;
import com.rauldetesan.price_comparator.dtos.BasketItemDTOS.BasketItemDTO;
import com.rauldetesan.price_comparator.enums.Unit;
import com.rauldetesan.price_comparator.exceptions.ResourceNotFoundException;
import com.rauldetesan.price_comparator.repositories.BasketRepository;
import com.rauldetesan.price_comparator.repositories.StoreProductRepository;
import com.rauldetesan.price_comparator.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasketService {

    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final StoreProductRepository storeProductRepository;

    @Autowired
    public BasketService(BasketRepository basketRepository, UserRepository userRepository, StoreProductRepository storeProductRepository) {
        this.basketRepository = basketRepository;
        this.userRepository = userRepository;
        this.storeProductRepository = storeProductRepository;
    }

    public void addBasket(BasketDTO dto){
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + dto.getUserId() + " not found"));

        Basket basket = new Basket();

        basket.setId(dto.getId());
        basket.setUser(user);
        basket.setItems(dto.getItems());
        basket.setCreatedAt(dto.getCreatedAt());

        basketRepository.save(basket);
    }

    public List<BasketResponseDTO> getBaskets() {
        return basketRepository.findAll()
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    public BasketResponseDTO entityToDTO(Basket basket) {

        BasketResponseDTO dto = new BasketResponseDTO();
        dto.setId(basket.getId());
        dto.setUserId(basket.getUser().getId());
        dto.setCreatedAt(basket.getCreatedAt());

        List<BasketItemDTO> itemDTOs = basket.getItems().stream()
                .map(this::basketItemToResponseDTO)
                .collect(Collectors.toList());

        dto.setItems(itemDTOs);

        return dto;
    }

    private BasketItemDTO basketItemToResponseDTO(BasketItem item) {
        BasketItemDTO dto = new BasketItemDTO();

        dto.setId(item.getId());
        dto.setProductName(item.getProductName());
        dto.setPreferredStore(item.getPreferredStore());

        return dto;
    }

    /**
     *
     * Goes through the items of basket with id basketId and extracts product name and store name
     * Uses findCheapestByProductName with every product name and store name found in the basket
     * After the method returns the cheaper products, if there are cheaper products, we map them to CheapestProductDTO and build the notification message
     * Messages consisting of every cheaper product found are sent to the owner of the basket
     *
     * @param basketId represents the id of the basket
     *
     */

    public List<CheapestProductDTO> findCheapestProductsForBasket(Long basketId){

        // Fetch the basket with basketId
        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new ResourceNotFoundException("Basket with id " + basketId + " not found"));

        // Fetch the owner of the basket
        User user = basket.getUser();

        // Initialize the notifications and the list of the DTOs
        List<String> notifications = new ArrayList<>();
        List<CheapestProductDTO> results = new ArrayList<>();

        for(BasketItem item : basket.getItems()){
            // For every item in the basket we save the productName and storeName for the findCheapestByProductName method
            String productName = item.getProductName();
            String storeName = item.getPreferredStore();

            // If there is not a preferred store, we set the storeName to null
            if(storeName.equals(""))
                storeName = null;

            List<Object[]> cheapest = storeProductRepository
                    .findCheapestByProductName(productName, storeName);

            // If the method returned something (cheaper products)
            if(!cheapest.isEmpty()){
                // We get the first product (the cheapest one)
                Object[] row = cheapest.get(0);
                String unitStr = (String) row[4];
                // Map the DTO with the response
                CheapestProductDTO dto = new CheapestProductDTO(
                        (String) row[0],
                        (String) row[1],
                        (String) row[8],
                        ((Number) row[5]).doubleValue(),
                        (String) row[6],
                        ((Number) row[3]).doubleValue(),
                        Unit.valueOf(unitStr),
                        ((Timestamp) row[7]).toLocalDateTime()
                );

                // Build the notification and add it to the notification list
                String message = "Produsul " + dto.getProductName() + " este cel mai ieftin la "
                        + dto.getPrice() + " " + dto.getCurrency() + " la magazinul " + dto.getStoreName();

                notifications.add(message);


                results.add(dto);
            }
        }

        // Send all the notifications to the user
        for(String notification: notifications){
            user.addNotification(notification);
        }

        userRepository.save(user);
        return results;
    }


}
