package org.inner.circle.o2oserver.order.presentation.dto

import org.inner.circle.o2oserver.order.domain.Menu
import org.inner.circle.o2oserver.order.domain.MenuOption
import org.inner.circle.o2oserver.order.domain.MenuOptionGroup
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
            fun toOrder(orderCreate: OrderCreate): Order {
                return Order(
                    storeId = orderCreate.storeId,
                    menus = MenuCreate.toMenus(orderCreate.menus),
                    price = orderCreate.orderPrice,
                    payment = orderCreate.payment,
                    addressId = orderCreate.addressId
                )
            }
        }
    }

    data class MenuCreate(
        val menuId: Long,
        val menuCount: Long,
        val options: List<OptionCreate>
    ) {
        companion object {
            fun toMenus(menuCreates: List<MenuCreate>): List<Menu> {
                return menuCreates.map { menuCreate ->
                    Menu(
                        menuId = menuCreate.menuId,
                        menuOptions =
                            menuCreate.options.map { menuOption ->
                                OptionCreate.toMenuOption(menuOption)
                            }
                    )
                }
            }
        }
    }

    data class OptionCreate(
        val optionGroupId: Long,
        val optionId: List<Long>
    ) {
        companion object {
            fun toMenuOption(optionCreate: OptionCreate): MenuOptionGroup {
                return MenuOptionGroup(
                    menuOptionGroupId = optionCreate.optionGroupId,
                    menuOptions =
                        optionCreate.optionId.map {
                            MenuOption(menuId = it)
                        }
                )
            }
        }
    }
}
