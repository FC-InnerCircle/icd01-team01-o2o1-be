package org.inner.circle.o2oserver.order.presentation.dto

import org.inner.circle.o2oserver.order.domain.Order

class OrderDetailResponse {
    data class OrderInfoDetail(
        private val orderId: Long,
        private val orderTime: String,
        private val orderStatus: String,
        private val orderPrice: Long,
        private val store: StoreInfo,
        private val menus: List<MenuInfo>,
        private val orderAddress: AddressInfo,
    ) {
        companion object {
            fun toResponse(order: Order): OrderInfoDetail {
                return OrderInfoDetail(
                    orderId = order.orderId ?: 0,
                    orderTime = order.orderTime.toString(),
                    orderStatus = order.orderStatus.toString(),
                    orderPrice = order.orderPrice,
                    store = StoreInfo.toResponse(order.store),
                    menus = order.menus.map { menu ->
                        MenuInfo.toResponse(menu)
                    },
                    orderAddress = AddressInfo.toResponse(order.orderAddress),
                )
            }
        }
    }

    data class StoreInfo(
        private val storeId: Long,
        private val storeName: String,
        private val storeAddress: AddressInfo,
    ) {
        companion object {
            fun toResponse(store: Order.Store): StoreInfo {
                return StoreInfo(
                    storeId = store.storeId,
                    storeName = store.storeName,
                    storeAddress = AddressInfo.toResponse(store.storeAddress),
                )
            }
        }
    }

    data class AddressInfo(
        private val addressId: Long,
        private val latitude: String,
        private val longitude: String,
        private val address: String,
        private val addressDetail: String,
        private val zipCode: String,
    ) {
        companion object {
            fun toResponse(address: Order.Address): AddressInfo {
                return AddressInfo(
                    addressId = address.addressId,
                    latitude = address.latitude.toString(),
                    longitude = address.longitude.toString(),
                    address = address.address ?: "",
                    addressDetail = address.detail ?: "",
                    zipCode = address.zipCode ?: "",
                )
            }
        }
    }

    data class MenuInfo(
        private val menuId: Long,
        private val menuName: String,
        private val menuCount: Int,
        private val optionGroup: List<OptionGroupInfo>,
    ) {
        companion object {
            fun toResponse(menu: Order.Menu): MenuInfo {
                return MenuInfo(
                    menuId = menu.menuId,
                    menuName = menu.menuName ?: "",
                    menuCount = menu.menuCount ?: 0,
                    optionGroup = menu.menuOptionGroups.map { menuOptionGroup ->
                        OptionGroupInfo.toResponse(menuOptionGroup)
                    },
                )
            }
        }
    }

    data class OptionGroupInfo(
        private val optionGroupId: Long,
        private val optionGroupName: String,
        private val option: List<OptionInfo>,
    ) {
        companion object {
            fun toResponse(menuOptionGroup: Order.MenuOptionGroup): OptionGroupInfo {
                return OptionGroupInfo(
                    optionGroupId = menuOptionGroup.menuOptionGroupId,
                    optionGroupName = menuOptionGroup.menuOptionName ?: "",
                    option = menuOptionGroup.menuOptions.map { menuOption ->
                        OptionInfo.toResponse(menuOption)
                    },
                )
            }
        }
    }

    data class OptionInfo(
        private val optionId: Long,
        private val optionName: String,
    ) {
        companion object {
            fun toResponse(menuOption: Order.MenuOption): OptionInfo {
                return OptionInfo(
                    optionId = menuOption.optionId,
                    optionName = menuOption.optionName ?: "",
                )
            }
        }
    }
}
