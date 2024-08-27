package org.inner.circle.o2oserver.order.domain

interface OrderReader {
    fun saveOrderCreate(order: Order): Order
}
