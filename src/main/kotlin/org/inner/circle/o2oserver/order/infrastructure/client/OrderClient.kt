package org.inner.circle.o2oserver.order.infrastructure.client

import io.github.oshai.kotlinlogging.KotlinLogging
import org.inner.circle.o2oserver.order.domain.Order
import org.inner.circle.o2oserver.order.domain.OrderReader
import org.inner.circle.o2oserver.order.infrastructure.dto.OrderClientResponse
import org.inner.circle.o2oserver.order.infrastructure.dto.OrderClientResponse.OrderCreateResult
import org.springframework.stereotype.Component

@Component
class OrderClient: OrderReader {
    private val log = KotlinLogging.logger {}

    override fun saveOrderCreate(
        order: Order): Order {
        return OrderClientResponse.OrderCreateResult.toDomain(
            OrderCreateResult(1, 2)
        )
    }
}
