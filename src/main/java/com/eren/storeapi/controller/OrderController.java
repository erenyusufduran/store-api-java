package com.eren.storeapi.controller;

import com.eren.storeapi.entity.Order;
import com.eren.storeapi.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public UUID createOrder(@Valid @RequestBody CreateOrderRequest request) {

        Order order = orderService.createOrder(
                request.storeId(),
                request.items()
        );

        return order.getId();
    }

    public record CreateOrderRequest(
            @NotNull
            UUID storeId,

            @NotNull
            List<OrderService.OrderRequestItem> items
    ) {}
}