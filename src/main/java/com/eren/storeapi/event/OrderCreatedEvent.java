package com.eren.storeapi.event;

import java.time.OffsetDateTime;
import java.util.UUID;

public record OrderCreatedEvent(
        UUID orderId,
        UUID storeId,
        OffsetDateTime createdAt
) {
}