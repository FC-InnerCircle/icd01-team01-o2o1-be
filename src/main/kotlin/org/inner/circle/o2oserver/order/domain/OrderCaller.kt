package org.inner.circle.o2oserver.order.domain

interface OrderCaller {
    fun saveOrderCall(order: Order)

    fun getOrderDetailCall(orderId: Long): Order
}
