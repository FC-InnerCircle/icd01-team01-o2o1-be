package org.inner.circle.o2oserver.order.domain

class Store(
    val storeId: Long,
    val name: String? = "",
    val address: Address,
    val menus: List<Menu>
)
