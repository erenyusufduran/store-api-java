package com.eren.storeapi.service;

import com.eren.storeapi.entity.*;
import com.eren.storeapi.exception.BadRequestException;
import com.eren.storeapi.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public OrderService(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            InventoryRepository inventoryRepository,
            ProductRepository productRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order createOrder(UUID storeId, List<OrderRequestItem> items) {
        if (items == null || items.isEmpty()) {
            throw new BadRequestException("order items cannot be empty");
        }

        Order order = new Order(UUID.randomUUID(), OffsetDateTime.now());
        orderRepository.save(order);

        for (OrderRequestItem item : items) {
            if (item.quantity() == null || item.quantity() <= 0) {
                throw new BadRequestException("quantity must be greater than 0");
            }

            Product product = productRepository.findById(item.productId())
                    .orElseThrow(() -> new BadRequestException("product not found"));

            Inventory inventory = inventoryRepository
                    .findByStoreIdAndProductId(storeId, item.productId())
                    .orElseThrow(() -> new BadRequestException("product not in store"));

            if (inventory.getQuantity() < item.quantity()) {
                throw new BadRequestException("insufficient stock");
            }

            inventory.setQuantity(inventory.getQuantity() - item.quantity());
            inventoryRepository.save(inventory);

            OrderItem orderItem = new OrderItem(
                    UUID.randomUUID(),
                    order,
                    product,
                    item.quantity()
            );

            orderItemRepository.save(orderItem);
        }

        return order;
    }

    public record OrderRequestItem(
            UUID productId,
            Integer quantity
    ) {}
}