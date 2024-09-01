package org.inner.circle.o2oserver.order.infrastructure.dto

class OrderClientRequest {
    data class CreateOrder(
        val orderId: Long,
        val storeId: Long,
    )
}
