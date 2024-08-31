package org.inner.circle.o2oserver.order.domain

interface OrderUseCase {
    fun createOrder(order: Order): Order

    fun getOrderDetail(orderId: Long): Order
}
