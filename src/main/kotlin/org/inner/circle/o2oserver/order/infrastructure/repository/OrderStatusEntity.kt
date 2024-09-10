package org.inner.circle.o2oserver.order.infrastructure.repository

import org.bson.BsonDocument
import org.inner.circle.o2oserver.commons.enums.OrderStatus
import org.inner.circle.o2oserver.order.domain.Order
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class OrderStatusEntity(
    private val orderId: Long,
    private val orderStatus: OrderStatus,
    private val orderTime: LocalDateTime,
) {
    companion object {
        fun watchToEntity(document: BsonDocument): OrderStatusEntity {
            return OrderStatusEntity(
                orderId = document.getInt64("orderId")?.longValue() ?: 0,
                orderStatus = OrderStatus.valueOf(document.getString("orderStatus").value),
                orderTime = Instant.ofEpochMilli(document.getTimestamp("orderTime").value)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime(),
            )
        }

        fun toDomain(orderStatusEntity: OrderStatusEntity): Order {
            return Order(
                orderId = orderStatusEntity.orderId,
                orderTime = orderStatusEntity.orderTime,
                orderStatus = orderStatusEntity.orderStatus,
            )
        }
    }
}
