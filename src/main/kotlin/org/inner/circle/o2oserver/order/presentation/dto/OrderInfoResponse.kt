package org.inner.circle.o2oserver.order.presentation.dto

import org.inner.circle.o2oserver.order.domain.Order
import org.inner.circle.o2oserver.order.domain.Store

class OrderInfoResponse {
    data class OrderInfoDetail(
        private val orderId: Long,
        private val orderTime: String,
        private val orderStatus: String,
        private val orderPrice: Long,
        private val store: StoreInfo,
        private val menus: List<MenuInfo>,
        private val address: AddressInfo
    ) {
        companion object {
            fun toResponse(
                order: Order,
                store: Store
            ): OrderInfoDetail {
                return OrderInfoDetail(
                    orderId = order.orderId ?: 0,
                    orderTime = order.orderTime.toString(),
                    orderStatus = order.orderStatus.toString(),
                    orderPrice = order.price ?: 0,
                    store =
                        StoreInfo(
                            storeId = order.storeId ?: 0,
                            storeName = store.name ?: "",
                            storeAddress =
                                AddressInfo(
                                    latitude = store.address.latitude.toString(),
                                    longitude = store.address.longitude.toString(),
                                    address = store.address.address,
                                    addressDetail = store.address.detail,
                                    zipCode = store.address.zipCode
                                )
                        ),
                    menus =
                        order.menus?.let {
                            it.map { menu ->
                                MenuInfo(
                                    menuId = menu.menuId,
                                    menuName = menu.name ?: "",
                                    menuCount = menu.count ?: 0,
                                    optionGroup = emptyList()
                                )
                            }
                        } ?: emptyList(),
                    address =
                        AddressInfo(
                            latitude = order.address?.latitude.toString(),
                            longitude = order.address?.longitude.toString(),
                            address = order.address?.address.toString(),
                            addressDetail = order.address?.detail.toString(),
                            zipCode = order.address?.zipCode.toString()
                        )
                )
                TODO("address, menu, menyOption 등 도메인 정리 필요")
            }
        }
    }

    data class StoreInfo(
        private val storeId: Long,
        private val storeName: String,
        private val storeAddress: AddressInfo
    )

    data class AddressInfo(
        private val latitude: String,
        private val longitude: String,
        private val address: String,
        private val addressDetail: String,
        private val zipCode: String
    )

    data class MenuInfo(
        private val menuId: Long,
        private val menuName: String,
        private val menuCount: Long,
        private val optionGroup: List<OptionGroupInfo>
    )

    data class OptionGroupInfo(
        private val optionGroupId: Long,
        private val optionGroupName: String,
        private val option: List<OptionInfo>
    )

    data class OptionInfo(
        private val optionId: Long,
        private val optionName: String
    )
}
