package org.inner.circle.o2oserver.order.infrastructure.dto

import org.inner.circle.o2oserver.order.domain.Order

class OrderClientRequest {
    data class createOrder(
        val orderId: Long,
        val storeId: Long
    )
}
