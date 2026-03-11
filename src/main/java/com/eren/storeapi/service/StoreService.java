package com.eren.storeapi.service;

import com.eren.storeapi.entity.Store;
import com.eren.storeapi.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public Store createStore(String name) {
        Store store = new Store(UUID.randomUUID(), name);
        return storeRepository.save(store);
    }

    public List<Store> getStores() {
        return storeRepository.findAll();
    }
}