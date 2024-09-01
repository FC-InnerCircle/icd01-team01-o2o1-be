package org.inner.circle.o2oserver.order.application


import org.inner.circle.o2oserver.order.domain.Order
import org.inner.circle.o2oserver.order.domain.OrderUseCase
import org.inner.circle.o2oserver.order.presentation.dto.OrderCreateRequest
import org.inner.circle.o2oserver.order.presentation.dto.OrderCreateResponse
import org.springframework.stereotype.Component

@Component
class OrderCommandFacade(
    private val orderService: OrderUseCase
) {
    fun createOrder(
        orderCreate: OrderCreateRequest.OrderCreate,
        userName: String
    ): OrderCreateResponse.OrderCreateResult {
        val memberId = 1L // get member id : userName -> memberId
        val store = Order.Store(
            storeId = orderCreate.storeId,
            storeName = "storeName",
            storeAddress = Order.Address(
                addressId = 1L,
                latitude = 0.0,
                longitude = 0.0,
                address = "address",
                detail = "detail",
                zipCode = "zipCode"
            )
        )
        val toOrder = OrderCreateRequest.OrderCreate.toOrder(orderCreate, memberId, store)
        val createOrder = orderService.createOrder(toOrder)
        return OrderCreateResponse.OrderCreateResult.toResponse(createOrder)
    }
}
