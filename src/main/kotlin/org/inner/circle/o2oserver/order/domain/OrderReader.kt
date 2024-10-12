package org.inner.circle.o2oserver.order.domain

interface OrderReader {
    fun findOrderIsNullable(orderId: Long): Order?

    fun findOrder(orderId: Long): Order

    fun findOrdersByMember(memberId: Long): List<Order>
}
