package org.inner.circle.o2oserver.order.infrastructure.repository

import org.inner.circle.o2oserver.order.domain.Menu
import org.inner.circle.o2oserver.order.domain.Order
import org.inner.circle.o2oserver.order.domain.OrderReader
import org.inner.circle.o2oserver.order.domain.OrderStatus
import org.springframework.stereotype.Repository

@Repository
class OrderRepository : OrderReader {
    override fun findByOrderDetail(orderId: Long): Order {
        return Order(
            orderId = 1,
            storeId = 1,
            menus =
                listOf(
                    Menu(menuId = 1, name = "menu1", price = 1000, description = "menu1", menuOptions = emptyList())
                ),
            price = 1000,
            orderStatus = OrderStatus.DELIVERED
        )
    }
}
