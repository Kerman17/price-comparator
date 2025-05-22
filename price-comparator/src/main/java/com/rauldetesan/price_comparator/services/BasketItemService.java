package com.rauldetesan.price_comparator.services;

import com.rauldetesan.price_comparator.domain.Basket;
import com.rauldetesan.price_comparator.domain.BasketItem;
import com.rauldetesan.price_comparator.dtos.BasketItemDTOS.BasketItemDTO;
import com.rauldetesan.price_comparator.dtos.BasketItemDTOS.BasketItemResponseDTO;
import com.rauldetesan.price_comparator.exceptions.ResourceNotFoundException;
import com.rauldetesan.price_comparator.repositories.BasketItemRepository;
import com.rauldetesan.price_comparator.repositories.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BasketItemService {

    private final BasketItemRepository basketItemRepository;
    private final BasketRepository basketRepository;

    @Autowired
    public BasketItemService(BasketItemRepository basketItemRepository, BasketRepository basketRepository) {
        this.basketItemRepository = basketItemRepository;
        this.basketRepository = basketRepository;
    }

    public void addBasketItem(BasketItemDTO dto){

        Basket basket = basketRepository.findById(dto.getBasketId())
                .orElseThrow(() -> new ResourceNotFoundException("Basket with id " + dto.getBasketId() + " not found"));

        BasketItem basketItem = new BasketItem();

        basketItem.setId(dto.getId());
        basketItem.setProductName(dto.getProductName().toLowerCase());
        basketItem.setBasket(basket);
        basketItem.setPreferredStore(dto.getPreferredStore().toLowerCase());

        basketItemRepository.save(basketItem);
    }

    public List<BasketItemResponseDTO> getBasketItems(){
        List<BasketItem> basketItems = basketItemRepository.findAll();

        List<BasketItemResponseDTO> dtos = new ArrayList<>();

        for(BasketItem basketItem : basketItems){
            BasketItemResponseDTO dto = responseEntityToDTO(basketItem);

            dtos.add(dto);
        }

        return dtos;
    }

    public void deleteBasketItem(Long id){
        basketItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Basket with id " + id + " not found"));

        basketItemRepository.deleteById(id);
    }


    public BasketItemDTO entityToDTO(BasketItem basketItem){
        BasketItemDTO dto = new BasketItemDTO();

        dto.setId(basketItem.getId());
        dto.setBasketId(basketItem.getId());
        dto.setPreferredStore(basketItem.getPreferredStore());
        dto.setProductName(basketItem.getProductName());

        return dto;
    }

    public BasketItemResponseDTO responseEntityToDTO(BasketItem basketItem){
        BasketItemResponseDTO dto = new BasketItemResponseDTO();

        dto.setId(basketItem.getId());
        dto.setPreferredStore(basketItem.getPreferredStore());
        dto.setProductName(basketItem.getProductName());


        return dto;
    }




}
