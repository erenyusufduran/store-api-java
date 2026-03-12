package com.eren.storeapi.listener;

import com.eren.storeapi.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedEventListener {

    private static final Logger log = LoggerFactory.getLogger(OrderCreatedEventListener.class);

    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("OrderCreatedEvent received. orderId={}, storeId={}, createdAt={}",
                event.orderId(),
                event.storeId(),
                event.createdAt());
    }
}