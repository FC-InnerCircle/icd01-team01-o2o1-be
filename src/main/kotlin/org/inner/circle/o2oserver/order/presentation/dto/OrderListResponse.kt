package org.inner.circle.o2oserver.order.presentation.dto

import org.inner.circle.o2oserver.order.domain.Order

class OrderListResponse {
    data class OrderListResponse(
        val orders: List<OrderInfo>,
    ) {
        companion object {
            fun toResponse(orders: List<Order>): OrderListResponse {
                return OrderListResponse(
                    orders = orders.map {
                        OrderInfo(
                            orderId = it.orderId ?: 0,
                            orderTime = it.orderTime.toString(),
                            orderStatus = it.orderStatus.toString(),
                            orderPrice = it.orderPrice,
                            storeId = it.store!!.storeId!!,
                            storeName = it.store!!.storeName,
                            menus = it.menus!!.map { menu ->
                                MenuInfo(
                                    menuId = menu.menuId,
                                    menuName = menu.menuName ?: "",
                                    menuCount = menu.menuCount ?: 0,
                                    optionGroup = menu.menuOptionGroups.map { menuGroup ->
                                        MenuOptionGroupInfo(
                                            optionGroupId = menuGroup.menuOptionGroupId,
                                            option = menuGroup.menuOptions.map { menuOption ->
                                                MenuOptionInfo(
                                                    optionId = menuOption.optionId,
                                                    optionName = menuOption.optionName ?: "",
                                                )
                                            },
                                        )
                                    },
                                )
                            },
                            isReviewed = false,
                        )
                    },
                )
            }
        }
    }

    data class OrderInfo(
        val orderId: Long,
        val orderTime: String,
        val orderStatus: String,
        val orderPrice: Long,
        val storeId: Long,
        val storeName: String,
        val menus: List<MenuInfo>,
        val isReviewed: Boolean = false,
    )

    data class MenuInfo(
        val menuId: Long,
        val menuName: String,
        val menuCount: Int,
        val optionGroup: List<MenuOptionGroupInfo>,
    )

    data class MenuOptionGroupInfo(
        val optionGroupId: Long,
        val option: List<MenuOptionInfo>,
    )

    data class MenuOptionInfo(
        val optionId: Long,
        val optionName: String,
    )
}
