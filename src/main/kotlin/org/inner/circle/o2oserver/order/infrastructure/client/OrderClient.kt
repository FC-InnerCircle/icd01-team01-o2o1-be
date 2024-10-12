package org.inner.circle.o2oserver.order.infrastructure.client

import org.inner.circle.o2oserver.commons.config.WebInterface
import org.inner.circle.o2oserver.order.domain.Order
import org.inner.circle.o2oserver.order.domain.OrderCaller
import org.inner.circle.o2oserver.order.domain.Review
import org.inner.circle.o2oserver.order.infrastructure.dto.OrderClientRequest.OrderCreate.Companion.toRequest
import org.springframework.stereotype.Component

@Component
class OrderClient(
    private val webInterface: WebInterface,
) : OrderCaller {
    override fun saveOrderCall(order: Order): Long {
        val orderRequest = toRequest(order)
        val orderResponse = webInterface.createOrder(orderRequest)
        return orderResponse.orderId
    }

    override fun getOrderDetailCall(orderId: Long): Order {
        TODO("Not yet implemented")
    }

    override fun saveOrderReviewCall(review: Review): Long {
        TODO("Not yet implemented")
    }
}
