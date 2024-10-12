package org.inner.circle.o2oserver.order.infrastructure.dto

import org.inner.circle.o2oserver.order.domain.Order

class OrderClientRequest {
    data class OrderCreate(
        val storeId: Long,
        val storeName: String,
        val menus: List<MenuCreate>,
        val orderPrice: Long,
        val payment: String? = "CARD",
        val address: AddressCreate,
    ) {
        companion object {
            fun toRequest(order: Order): OrderCreate {
                return OrderCreate(
                    storeId = order.store?.storeId ?: 0,
                    storeName = order.store?.storeName ?: "",
                    menus = order.menus?.map { menu ->
                        MenuCreate(
                            menuId = menu.menuId,
                            menuName = menu.menuName ?: "",
                            menuCount = menu.menuCount ?: 0,
                            menuPrice = menu.menuPrice ?: 0,
                            optionGroups = menu.menuOptionGroups.map { optionGroup ->
                                OptionGroupCreate(
                                    optionGroupId = optionGroup.menuOptionGroupId,
                                    optionGroupName = optionGroup.menuOptionName ?: "",
                                    options = optionGroup.menuOptions.map { option ->
                                        OptionCreate(
                                            optionId = option.optionId,
                                            optionName = option.optionName ?: "",
                                            optionPrice = option.optionPrice ?: 0,
                                        )
                                    },
                                )
                            },
                        )
                    } ?: emptyList(),
                    orderPrice = order.orderPrice,
                    payment = order.payment,
                    address = AddressCreate(
                        addressId = order.store?.storeAddress?.addressId ?: 0,
                        address = order.store?.storeAddress?.address ?: "",
                        addressDetail = order.store?.storeAddress?.addressDetail ?: "",
                        zipCode = order.store?.storeAddress?.zipCode ?: "",
                        latitude = order.store?.storeAddress?.latitude ?: 0.0,
                        longitude = order.store?.storeAddress?.longitude ?: 0.0,
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
        val optionGroupName: String,
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
