package org.inner.circle.o2oserver.order.domain

class Menu(
    val menuId: Long,
    val name: String? = "",
    val price: Long? = 0L,
    val description: String? = "",
    val menuOptions: List<MenuOptionGroup>,
) {
}
