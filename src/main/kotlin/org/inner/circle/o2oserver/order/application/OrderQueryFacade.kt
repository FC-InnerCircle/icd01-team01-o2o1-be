package org.inner.circle.o2oserver.order.application

import org.inner.circle.o2oserver.order.domain.Address
import org.inner.circle.o2oserver.order.domain.OrderUseCase
import org.inner.circle.o2oserver.order.domain.Store
import org.inner.circle.o2oserver.order.presentation.dto.OrderInfoResponse
import org.springframework.stereotype.Component

@Component
class OrderQueryFacade(
    private val orderService: OrderUseCase
) {
    fun getOrderDetail(orderId: Long): OrderInfoResponse.OrderInfoDetail {
        val order = orderService.getOrderDetail(orderId)
        val store =
            Store(
                storeId = order.storeId!!,
                name = "Store Name",
                address =
                    Address(
                        addressId = 1,
                        address = "Address",
                        detail = "Detail",
                        zipCode = "ZipCode",
                        latitude = 1.0,
                        longitude = 1.0,
                        isDefault = true
                    ),
                menus = listOf()
            )
        return OrderInfoResponse.OrderInfoDetail.toResponse(order, store)
        TODO("임시 store 정보는 추후 변경 필요")
    }
}
