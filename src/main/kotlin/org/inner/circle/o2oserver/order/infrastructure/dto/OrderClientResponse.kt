package org.inner.circle.o2oserver.order.infrastructure.dto

import org.inner.circle.o2oserver.order.domain.Order

class OrderClientResponse {
    data class OrderCreateResult(
        val orderId: Long,
        val storeId: Long
    ) {
        companion object {
            fun toDomain(orderCreateResult: OrderCreateResult): Order {
                return Order(
                    orderId = orderCreateResult.orderId,
                    storeId = orderCreateResult.storeId
                )
            }
        }
    }
}
