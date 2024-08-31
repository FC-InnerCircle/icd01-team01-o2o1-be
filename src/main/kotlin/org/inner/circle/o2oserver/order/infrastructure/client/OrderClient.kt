package org.inner.circle.o2oserver.order.infrastructure.client

import org.inner.circle.o2oserver.order.domain.Order
import org.inner.circle.o2oserver.order.domain.OrderStore
import org.inner.circle.o2oserver.order.infrastructure.dto.OrderClientResponse
import org.inner.circle.o2oserver.order.infrastructure.dto.OrderClientResponse.OrderCreateResult
import org.springframework.stereotype.Component

@Component
class OrderClient : OrderStore {
    override fun saveOrderCreate(order: Order): Order {
        return OrderClientResponse.OrderCreateResult.toDomain(
            OrderCreateResult(1, 2)
        )
    }
}
