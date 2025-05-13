package com.rauldetesan.price_comparator.services;

import com.rauldetesan.price_comparator.domain.Store;
import com.rauldetesan.price_comparator.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public Store findStoreById(Long id){
        return storeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Store with id: " + id + " does not exist"));
    }

    public void addStore(Store store){
        storeRepository.save(store);
    }
}
