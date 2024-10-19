package org.inner.circle.o2oserver.order.domain

interface OrderStore {
    fun saveOrder(order: Order, orderId: Long): Order

    fun cancelOrder(orderId: Long): Long

    fun saveReview(review: Review): Review
}
