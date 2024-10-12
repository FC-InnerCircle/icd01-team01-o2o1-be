package org.inner.circle.o2oserver.order.domain

interface OrderUseCase {
    fun createOrder(order: Order): Order

    fun getOrderDetail(orderId: Long): Order

    fun getOrderList(memberId: Long): List<Order>

    fun cancelOrder(orderId: Long, memberId: Long): Long

    fun createReviewOrder(review: Review): Review
}
