package org.inner.circle.o2oserver.order.presentation.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.inner.circle.o2oserver.order.domain.Order

class OrderCreateRequest {
    data class OrderCreate(
        @field:NotNull(message = "스토어 ID는 필수입니다.")
        val storeId: Long,

        @field:NotBlank(message = "스토어 이름은 필수입니다.")
        val storeName: String,

        @field:NotEmpty(message = "메뉴는 비어 있을 수 없습니다.")
        val menus: List<MenuCreate>,

        @field:Min(value = 0, message = "주문 금액은 0 이상이어야 합니다.")
        val orderPrice: Long,

        @field:Pattern(regexp = "CARD", message = "주문방식은 CARD만 가능합니다.")
        val payment: String? = "CARD",

        @field:Valid
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
                                    menuOptionName = optionGroup.optionGroupName,
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
        @field:NotNull(message = "메뉴 ID는 필수입니다.")
        val menuId: Long,

        @field:NotBlank(message = "메뉴명은 필수입니다.")
        val menuName: String,

        @field:Min(value = 1, message = "메뉴 개수는 1 이상이어야 합니다.")
        val menuCount: Int,

        @field:Min(value = 0, message = "메뉴 금액은 0 이상이어야 합니다.")
        val menuPrice: Long,

        @field:NotEmpty(message = "옵션 그룹은 비어 있을 수 없습니다.")
        val optionGroups: List<OptionGroupCreate>,
    )

    data class OptionGroupCreate(
        @field:NotNull(message = "옵션 그룹 ID는 필수입니다.")
        val optionGroupId: Long,

        @field:NotBlank(message = "옵션 그룹 이름은 필수입니다.")
        val optionGroupName: String,

        @field:NotEmpty(message = "옵션은 비어 있을 수 없습니다.")
        val options: List<OptionCreate>,
    )

    data class OptionCreate(
        @field:NotNull(message = "옵션 상세 ID는 필수입니다.")
        val optionId: Long,

        @field:NotBlank(message = "옵션 상세 이름은 필수입니다.")
        val optionName: String,

        @field:Min(value = 0, message = "옵션 가격은 0 이상이어야 합니다.")
        val optionPrice: Long,
    )

    data class AddressCreate(
        @field:NotNull(message = "주소 ID는 필수입니다.")
        val addressId: Long,

        @field:NotBlank(message = "주소는 필수입니다.")
        val address: String,

        @field:NotBlank(message = "상세 주소는 필수입니다.")
        val addressDetail: String,

        @field:NotBlank(message = "우편번호는 필수입니다.")
        val zipCode: String,

        @field:DecimalMin(value = "0.0", message = "위도는 0.0 이상이어야 합니다.")
        val latitude: Double,

        @field:DecimalMin(value = "0.0", message = "경도는 0.0 이상이어야 합니다.")
        val longitude: Double,
    )
}
