package org.inner.circle.o2oserver.order.domain

import kotlinx.coroutines.flow.Flow

interface OrderUseCase {
    fun createOrder(order: Order): Order

    fun getOrderDetail(orderId: Long): Order

    fun getOrderList(memberId: Long): List<Order>

    fun cancelOrder(orderId: Long, memberId: Long): Long

    fun deliverySubscribe(orderId: Long, memberId: Long): Flow<Delivery>

    fun orderStatusSubscribe(orderId: Long, memberId: Long): Flow<Order>
}
