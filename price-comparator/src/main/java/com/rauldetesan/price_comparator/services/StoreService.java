package com.rauldetesan.price_comparator.services;

import com.rauldetesan.price_comparator.domain.Store;
import com.rauldetesan.price_comparator.repositories.StoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public List<Store> findAllStores(){
        return storeRepository.findAll();
    }

    public void addStore(Store store){
        storeRepository.save(store);
    }

    public void deleteStoreById(Long id){

        // First we search if the store exists
        storeRepository.findById(id)
                        .orElseThrow(() -> new NoSuchElementException("Store with id: " + id + " does not exist"));

        storeRepository.deleteById(id);
    }

    @Transactional
    public void updateStoreById(Long id,
                            String name){

        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Store with id: " + id + " does not exist"));

        if(name != null && name.length()>0)
            store.setName(name);

        storeRepository.save(store);

    }
}
