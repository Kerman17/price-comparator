package com.rauldetesan.price_comparator.services;

import com.rauldetesan.price_comparator.domain.Store;
import com.rauldetesan.price_comparator.dtos.StoreDTOS.StoreDTO;
import com.rauldetesan.price_comparator.exceptions.ResourceNotFoundException;
import com.rauldetesan.price_comparator.repositories.StoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public StoreDTO findStoreById(Long id){
         Store store = storeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Store with id: " + id + " does not exist"));

         StoreDTO dto = new StoreDTO();

         dto.setId(store.getId());
         dto.setName(store.getName());

         return dto;

    }

    public List<StoreDTO> findAllStores(){
        List<Store> stores = storeRepository.findAll();

        List<StoreDTO> dtos = new ArrayList<>();

        for(Store store : stores){
            StoreDTO dto = new StoreDTO();

            dto.setName(store.getName());
            dto.setId(store.getId());

            dtos.add(dto);
        }

        return dtos;
    }

    public void addStore(Store store){
        storeRepository.save(store);
    }

    public void deleteStoreById(Long id){

        // First we search if the store exists
        storeRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Store with id: " + id + " does not exist"));

        storeRepository.deleteById(id);
    }

    @Transactional
    public void updateStoreById(Long id,
                            String name){

        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store with id: " + id + " does not exist"));

        if(name != null && name.length()>0)
            store.setName(name);

        storeRepository.save(store);

    }
}
