package org.inner.circle.o2oserver.order.presentation.dto

import org.inner.circle.o2oserver.order.domain.Order

class OrderCreateRequest {
    data class OrderCreate(
        val storeId: Long,
        val menus: List<MenuCreate>,
        val orderPrice: Long,
        val payment: String,
        val addressId: Long
    ) {
        companion object {
            fun toOrder(orderCreate: OrderCreate, memberId: Long, store: Order.Store): Order {
                return Order(
                    store = store,
                    memberId = memberId,
                    menus = orderCreate.menus.map { menu ->
                        Order.Menu(
                            menuId = menu.menuId,
                            menuCount = menu.menuCount,
                            menuOptionGroups = menu.optionGroups.map { optionGroup ->
                                Order.MenuOptionGroup(
                                    menuOptionGroupId = optionGroup.optionGroupId,
                                    menuOptions = optionGroup.options.map { option ->
                                        Order.MenuOption(
                                            optionId = option.optionId
                                        )
                                    }
                                )
                            }
                        )
                    },
                    orderPrice = orderCreate.orderPrice,
                    payment = orderCreate.payment,
                    orderAddress = Order.Address(
                        addressId = orderCreate.addressId
                    )
                )
            }
        }
    }

    data class MenuCreate(
        val menuId: Long,
        val menuCount: Int,
        val optionGroups: List<OptionGroupCreate>
    )

    data class OptionGroupCreate(
        val optionGroupId: Long,
        val options: List<OptionCreate>
    )

    data class OptionCreate(
        val optionId: Long,
    )
}
