package org.inner.circle.o2oserver.order.infrastructure.client

import org.inner.circle.o2oserver.commons.config.WebInterface
import org.inner.circle.o2oserver.order.domain.Order
import org.inner.circle.o2oserver.order.domain.OrderCaller
import org.springframework.stereotype.Component

@Component
class OrderClient(
    private val webInterface: WebInterface
) : OrderCaller {
    override fun saveOrderCall(order: Order) {
//        webInterface.createOrder(order)
        TODO("2팀 연동 시 해당 API 사용")
    }

    override fun getOrderDetailCall(orderId: Long): Order {
        TODO("Not yet implemented")
    }

}
