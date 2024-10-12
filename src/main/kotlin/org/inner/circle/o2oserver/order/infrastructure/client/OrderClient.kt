package org.inner.circle.o2oserver.order.infrastructure.client

import org.inner.circle.o2oserver.commons.config.WebInterface
import org.inner.circle.o2oserver.order.domain.Order
import org.inner.circle.o2oserver.order.domain.OrderCaller
import org.inner.circle.o2oserver.order.infrastructure.dto.OrderClientRequest.OrderCreate.Companion.toRequest
import org.springframework.stereotype.Component

@Component
class OrderClient(
    private val webInterface: WebInterface,
) : OrderCaller {
    override fun saveOrderCall(order: Order) {
        val orderRequest = toRequest(order)
        webInterface.createOrder(orderRequest)
        TODO("2팀 연동 시 해당 API 사용")
    }

    override fun getOrderDetailCall(orderId: Long): Order {
        TODO("Not yet implemented")
    }
}
