package org.inner.circle.o2oserver.order.domain

import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderStore: OrderStore,
    private val orderReader: OrderReader,
    private val orderCaller: OrderCaller,
) : OrderUseCase {
    override fun createOrder(order: Order): Order {
        orderCaller.saveOrderCall(order)
        return orderStore.saveOrder(order)
    }

    override fun getOrderDetail(orderId: Long): Order {
        // cache or mongodb 후 없다면 api 호출
        // 만약 api 호출했다면 cache or mongodb에 저장
        return orderReader.findOrderDetailByOrderId(orderId)
    }

    override fun getOrderList(memberId: Long): List<Order> {
        return orderReader.findOrderListByMemberId(memberId)
    }
}
