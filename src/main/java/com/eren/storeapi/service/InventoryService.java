package com.eren.storeapi.service;

import com.eren.storeapi.entity.Inventory;
import com.eren.storeapi.entity.Product;
import com.eren.storeapi.entity.Store;
import com.eren.storeapi.exception.BadRequestException;
import com.eren.storeapi.exception.NotFoundException;
import com.eren.storeapi.repository.InventoryRepository;
import com.eren.storeapi.repository.ProductRepository;
import com.eren.storeapi.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;

    public InventoryService(
            InventoryRepository inventoryRepository,
            StoreRepository storeRepository,
            ProductRepository productRepository
    ) {
        this.inventoryRepository = inventoryRepository;
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
    }

    public Inventory addStock(UUID storeId, UUID productId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new BadRequestException("quantity must be greater than 0");
        }

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException("store not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("product not found"));

        Inventory inventory = inventoryRepository.findByStoreIdAndProductId(storeId, productId)
                .orElseGet(() -> new Inventory(UUID.randomUUID(), store, product, 0));

        inventory.setQuantity(inventory.getQuantity() + quantity);

        return inventoryRepository.save(inventory);
    }

    public List<Inventory> getStoreInventory(UUID storeId) {
        if (!storeRepository.existsById(storeId)) {
            throw new NotFoundException("store not found");
        }

        return inventoryRepository.findByStoreId(storeId);
    }
}