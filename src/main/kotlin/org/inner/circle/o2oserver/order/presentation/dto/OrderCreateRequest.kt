package org.inner.circle.o2oserver.order.presentation.dto

import org.inner.circle.o2oserver.order.domain.Order

class OrderCreateRequest {
    data class OrderCreate(
        val storeId: Long,
        val storeName: String,
        val menus: List<MenuCreate>,
        val orderPrice: Long,
        val payment: String? = "CARD",
        val address: AddressCreate,
    ) {
        companion object {
            fun toOrder(orderCreate: OrderCreate, memberId: Long): Order {
                return Order(
                    store = Order.Store(
                        storeId = orderCreate.storeId,
                        storeName = orderCreate.storeName,
                        storeAddress = null,
                    ),
                    memberId = memberId,
                    menus = orderCreate.menus.map { menu ->
                        Order.Menu(
                            menuId = menu.menuId,
                            menuName = menu.menuName,
                            menuCount = menu.menuCount,
                            menuPrice = menu.menuPrice,
                            menuOptionGroups = menu.optionGroups.map { optionGroup ->
                                Order.MenuOptionGroup(
                                    menuOptionGroupId = optionGroup.optionGroupId,
                                    menuOptionName = optionGroup.optionName,
                                    menuOptions = optionGroup.options.map { option ->
                                        Order.MenuOption(
                                            optionId = option.optionId,
                                            optionName = option.optionName,
                                            optionPrice = option.optionPrice,
                                        )
                                    },
                                )
                            },
                        )
                    },
                    orderPrice = orderCreate.orderPrice,
                    payment = orderCreate.payment ?: "CARD",
                    orderAddress = Order.Address(
                        addressId = orderCreate.address.addressId,
                        address = orderCreate.address.address,
                        addressDetail = orderCreate.address.addressDetail,
                        zipCode = orderCreate.address.zipCode,
                        latitude = orderCreate.address.latitude,
                        longitude = orderCreate.address.longitude,
                    ),
                )
            }
        }
    }

    data class MenuCreate(
        val menuId: Long,
        val menuName: String,
        val menuCount: Int,
        val menuPrice: Long,
        val optionGroups: List<OptionGroupCreate>,
    )

    data class OptionGroupCreate(
        val optionGroupId: Long,
        val optionName: String,
        val options: List<OptionCreate>,
    )

    data class OptionCreate(
        val optionId: Long,
        val optionName: String,
        val optionPrice: Long,
    )

    data class AddressCreate(
        val addressId: Long,
        val address: String,
        val addressDetail: String,
        val zipCode: String,
        val latitude: Double,
        val longitude: Double,
    )
}
