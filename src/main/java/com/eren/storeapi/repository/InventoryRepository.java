package com.eren.storeapi.repository;

import com.eren.storeapi.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {

    Optional<Inventory> findByStoreIdAndProductId(UUID storeId, UUID productId);

    List<Inventory> findByStoreId(UUID storeId);
}