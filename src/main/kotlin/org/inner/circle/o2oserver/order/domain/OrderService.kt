package org.inner.circle.o2oserver.order.domain

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service


@Service
class OrderService(
    private val orderReader: OrderReader
): OrderUseCase {

    private val log = KotlinLogging.logger {}

    override fun createOrder(
        order: Order
    ): Order {
        return orderReader.saveOrderCreate(order)
    }
}
