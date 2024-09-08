package org.inner.circle.o2oserver.order.infrastructure.repository

import org.bson.types.ObjectId
import org.inner.circle.o2oserver.commons.enums.OrderStatus
import org.inner.circle.o2oserver.order.domain.Order
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.time.ZoneId

@Document(collection = "order")
class OrderEntity(
    @Id
    val id: ObjectId = ObjectId.get(),
    val orderId: Long,
    val orderTime: LocalDateTime,
    var orderStatus: OrderStatus,
    val orderPrice: Long,
    val memberId: Long,
    val store: StoreField,
    val menus: List<MenuField>,
    val payment: String = "CARD",
    val address: AddressField,
    val createdAt: LocalDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul")),
) {
    companion object {
        fun toDomain(orderEntity: OrderEntity): Order {
            return Order(
                orderId = orderEntity.orderId,
                orderTime = orderEntity.orderTime,
                orderStatus = orderEntity.orderStatus,
                memberId = orderEntity.memberId,
                orderPrice = orderEntity.orderPrice,
                store = Order.Store(
                    storeId = orderEntity.store.storeId ?: 0,
                    storeName = orderEntity.store.storeName ?: "",
                    storeAddress = orderEntity.store.storeAddress?.let {
                        Order.Address(
                            addressId = orderEntity.store.storeAddress.addressId,
                            address = orderEntity.store.storeAddress.address,
                            addressDetail = orderEntity.store.storeAddress.addressDetail ?: "",
                            zipCode = orderEntity.store.storeAddress.zipCode,
                            latitude = orderEntity.store.storeAddress.latitude,
                            longitude = orderEntity.store.storeAddress.longitude,
                        )
                    },
                ),
                menus = orderEntity.menus.map { menu ->
                    Order.Menu(
                        menuId = menu.menuId ?: 0,
                        menuName = menu.menuName ?: "",
                        menuPrice = menu.menuPrice ?: 0,
                        menuCount = menu.menuCount ?: 0,
                        description = "",
                        menuOptionGroups = menu.menuGroup.map { group ->
                            Order.MenuOptionGroup(
                                menuOptionGroupId = group.menuGroupId ?: 0,
                                menuOptionName = group.name ?: "",
                                menuOptions = group.menuOptions.map { option ->
                                    Order.MenuOption(
                                        optionId = option.menuOptionId ?: 0,
                                        optionName = option.name ?: "",
                                    )
                                },
                            )
                        },
                    )
                },
                orderAddress = Order.Address(
                    addressId = orderEntity.address.addressId,
                    address = orderEntity.address.address,
                    addressDetail = orderEntity.address.addressDetail ?: "",
                    zipCode = orderEntity.address.zipCode,
                    latitude = orderEntity.address.latitude,
                    longitude = orderEntity.address.longitude,
                ),
            )
        }

        fun toEntity(order: Order): OrderEntity {
            return OrderEntity(
                orderId = order.orderId ?: 0,
                orderTime = order.orderTime ?: LocalDateTime.now(),
                orderStatus = order.orderStatus ?: OrderStatus.PENDING,
                orderPrice = order.orderPrice,
                memberId = order.memberId,
                store = StoreField(
                    storeId = order.store.storeId,
                    storeName = order.store.storeName,
                    storeAddress = order.store.storeAddress.let { address ->
                        address?.let {
                            AddressField(
                                addressId = address.addressId,
                                latitude = address.latitude,
                                longitude = address.longitude,
                                address = address.address,
                                addressDetail = address.addressDetail,
                                zipCode = address.zipCode,
                            )
                        }
                    },
                ),
                menus = order.menus.map {
                    MenuField(
                        menuId = it.menuId,
                        menuName = it.menuName,
                        menuCount = it.menuCount,
                        menuPrice = it.menuPrice,
                        menuGroup = it.menuOptionGroups.map { group ->
                            MenuGroupField(
                                menuGroupId = group.menuOptionGroupId,
                                name = group.menuOptionName,
                                menuOptions = group.menuOptions.map { option ->
                                    MenuOptionField(
                                        menuOptionId = option.optionId,
                                        name = option.optionName,
                                    )
                                },
                            )
                        },
                    )
                },
                address = AddressField(
                    addressId = order.orderAddress.addressId,
                    latitude = order.orderAddress.latitude,
                    longitude = order.orderAddress.longitude,
                    address = order.orderAddress.address,
                    addressDetail = order.orderAddress.addressDetail,
                    zipCode = order.orderAddress.zipCode,
                ),
            )
        }

        fun cancelOrder(orderEntity: OrderEntity): OrderEntity {
            return orderEntity.apply {
                orderStatus = OrderStatus.CANCELED
            }
        }
    }

    data class AddressField(
        val addressId: Long,
        val latitude: Double,
        val longitude: Double,
        val address: String,
        val addressDetail: String? = "",
        val zipCode: String,
    )

    data class MenuField(
        val menuId: Long? = 0,
        val menuName: String? = "",
        val menuCount: Int? = 0,
        val menuPrice: Long? = 0,
        val menuGroup: List<MenuGroupField>,
    )

    data class MenuGroupField(
        val menuGroupId: Long? = 0,
        val name: String? = "",
        val isRequired: Boolean? = false,
        val menuOptions: List<MenuOptionField>,
    )

    data class MenuOptionField(
        val menuOptionId: Long? = 0,
        val name: String? = "",
    )

    data class StoreField(
        val storeId: Long? = 0,
        val storeName: String? = "",
        val storeAddress: AddressField?,
    )
}
