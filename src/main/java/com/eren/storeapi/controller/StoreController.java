package com.eren.storeapi.controller;

import com.eren.storeapi.entity.Store;
import com.eren.storeapi.service.StoreService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public StoreResponse createStore(@Valid @RequestBody CreateStoreRequest request) {
        Store store = storeService.createStore(request.name());
        return StoreResponse.from(store);
    }

    @GetMapping
    public List<StoreResponse> getStores() {
        return storeService.getStores()
                .stream()
                .map(StoreResponse::from)
                .toList();
    }

    public record CreateStoreRequest(
            @NotBlank(message = "name is required")
            String name
    ) {
    }

    public record StoreResponse(
            UUID id,
            String name
    ) {
        public static StoreResponse from(Store store) {
            return new StoreResponse(store.getId(), store.getName());
        }
    }
}