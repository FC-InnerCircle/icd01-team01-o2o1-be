package org.inner.circle.o2oserver.order.domain

import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderStore: OrderStore,
    private val orderReader: OrderReader
) : OrderUseCase {
    override fun createOrder(order: Order): Order {
        return orderStore.saveOrderCreate(order)
    }

    override fun getOrderDetail(orderId: Long): Order {
        return orderReader.findByOrderDetail(orderId)
    }
}
