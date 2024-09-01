package org.inner.circle.o2oserver.order.domain

interface OrderReader {
    fun findOrderDetailByOrderId(orderId: Long): Order

    fun findOrderListByMemberId(memberId: Long): List<Order>
}
