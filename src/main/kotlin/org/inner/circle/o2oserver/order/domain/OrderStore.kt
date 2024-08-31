package org.inner.circle.o2oserver.order.domain

interface OrderStore {
    fun saveOrderCreate(order: Order): Order
}
