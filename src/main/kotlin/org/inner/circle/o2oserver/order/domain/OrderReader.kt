package org.inner.circle.o2oserver.order.domain

import kotlinx.coroutines.flow.Flow

interface OrderReader {
    fun findOrderDetailByOrderId(orderId: Long): Order

    fun findOrderListByMemberId(memberId: Long): List<Order>

    fun subscribeDelivery(orderId: Long, memberId: Long): Flow<Delivery>

    fun subscribeOrder(orderId: Long, memberId: Long): Flow<Order>
}
