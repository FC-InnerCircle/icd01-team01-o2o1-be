package org.inner.circle.o2oserver.order.domain

interface OrderCaller {
    fun saveOrderCall(order: Order): Long

    fun getOrderDetailCall(orderId: Long): Order

    fun saveOrderReviewCall(review: Review): Long
}
