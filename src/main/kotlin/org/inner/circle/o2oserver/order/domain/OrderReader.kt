package org.inner.circle.o2oserver.order.domain

interface OrderReader {
    fun findByOrderDetail(orderId: Long): Order
}
