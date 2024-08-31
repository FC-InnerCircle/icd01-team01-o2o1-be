package org.inner.circle.o2oserver.order.domain

import java.time.LocalDateTime

class Order(
    val orderId: Long? = 0,
    val storeId: Long? = 0,
    val menus: List<Menu>? = null,
    val price: Long? = 0,
    val payment: String? = "CARD",
    val addressId: Long? = 0,
    val orderStatus: OrderStatus? = OrderStatus.PENDING,
    val orderTime: LocalDateTime? = LocalDateTime.now(),
    val address: Address? = null
)
