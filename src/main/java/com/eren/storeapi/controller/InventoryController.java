package com.eren.storeapi.controller;

import com.eren.storeapi.entity.Inventory;
import com.eren.storeapi.service.InventoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/inventories")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/add")
    public InventoryResponse addStock(@Valid @RequestBody AddStockRequest request) {
        Inventory inventory = inventoryService.addStock(
                request.storeId(),
                request.productId(),
                request.quantity()
        );

        return InventoryResponse.from(inventory);
    }

    @GetMapping("/store/{storeId}")
    public List<InventoryResponse> getStoreInventory(@PathVariable UUID storeId) {
        return inventoryService.getStoreInventory(storeId)
                .stream()
                .map(InventoryResponse::from)
                .toList();
    }

    public record AddStockRequest(
            @NotNull(message = "storeId is required")
            UUID storeId,

            @NotNull(message = "productId is required")
            UUID productId,

            @NotNull(message = "quantity is required")
            @Min(value = 1, message = "quantity must be at least 1")
            Integer quantity
    ) {
    }

    public record InventoryResponse(
            UUID id,
            UUID storeId,
            String storeName,
            UUID productId,
            String productName,
            Integer quantity
    ) {
        public static InventoryResponse from(Inventory inventory) {
            return new InventoryResponse(
                    inventory.getId(),
                    inventory.getStore().getId(),
                    inventory.getStore().getName(),
                    inventory.getProduct().getId(),
                    inventory.getProduct().getName(),
                    inventory.getQuantity()
            );
        }
    }
}