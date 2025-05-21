package com.rauldetesan.price_comparator.services;

import com.rauldetesan.price_comparator.domain.Basket;
import com.rauldetesan.price_comparator.domain.BasketItem;
import com.rauldetesan.price_comparator.domain.User;
import com.rauldetesan.price_comparator.dtos.BasketDTOS.BasketDTO;
import com.rauldetesan.price_comparator.dtos.BasketDTOS.BasketResponseDTO;
import com.rauldetesan.price_comparator.dtos.BasketItemDTOS.BasketItemDTO;
import com.rauldetesan.price_comparator.dtos.BasketItemDTOS.BasketItemResponseDTO;
import com.rauldetesan.price_comparator.exceptions.ResourceNotFoundException;
import com.rauldetesan.price_comparator.repositories.BasketRepository;
import com.rauldetesan.price_comparator.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasketService {

    private final BasketRepository basketRepository;
    private final UserRepository userRepository;

    @Autowired
    public BasketService(BasketRepository basketRepository, UserRepository userRepository) {
        this.basketRepository = basketRepository;
        this.userRepository = userRepository;
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
}
