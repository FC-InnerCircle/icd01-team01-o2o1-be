package org.inner.circle.o2oserver.order.domain

import org.inner.circle.o2oserver.commons.enums.OrderStatus
import java.time.LocalDateTime

class Order(
    val orderId: Long? = 0,
    val orderTime: LocalDateTime? = LocalDateTime.now(),
    val orderStatus: OrderStatus? = OrderStatus.PENDING,
    val orderPrice: Long = 0,
    val memberId: Long = 0,
    val store: Store? = null,
    val menus: List<Menu>? = null,
    val payment: String = "CARD",
    val orderAddress: Address? = null,
) {
    class Address(
        val addressId: Long,
        val address: String,
        val addressDetail: String,
        val zipCode: String,
        val latitude: Double,
        val longitude: Double,
    )

    class Menu(
        val menuId: Long,
        val menuName: String? = "",
        val menuPrice: Long? = 0,
        val menuCount: Int? = 0,
        val description: String? = "",
        val menuOptionGroups: List<MenuOptionGroup>,
    )

    class MenuOptionGroup(
        val menuOptionGroupId: Long,
        val menuOptionName: String? = "",
        val menuOptions: List<MenuOption>,
    )

    class MenuOption(
        val optionId: Long,
        val optionName: String? = "",
        val optionPrice: Long? = 0,
    )

    class Store(
        val storeId: Long?,
        val storeName: String,
        val storeAddress: Address?,
    )
}
