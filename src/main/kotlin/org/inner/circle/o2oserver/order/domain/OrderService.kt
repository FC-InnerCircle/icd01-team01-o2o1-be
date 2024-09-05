package org.inner.circle.o2oserver.order.domain

import org.inner.circle.o2oserver.commons.exception.Exceptions.UnCancellableStatusException
import org.inner.circle.o2oserver.commons.enums.OrderStatus.*
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderStore: OrderStore,
    private val orderReader: OrderReader,
//    private val orderCaller: OrderCaller,
) : OrderUseCase {
    override fun createOrder(order: Order): Order {
//        orderCaller.saveOrderCall(order)
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

    override fun cancelOrder(orderId: Long, memberId: Long): Long {
        val order = orderReader.findOrderDetailByOrderId(orderId)

        if (order.memberId != memberId) {
            throw IllegalArgumentException("주문자가 아닙니다.")
        }

        when (order.orderStatus) {
            PENDING -> PENDING.name
            ACCEPTED -> ACCEPTED.name
            PREPARING -> throw UnCancellableStatusException("조리 중인 주문입니다.")
            DELIVERING -> throw UnCancellableStatusException("배송 중인 주문입니다.")
            DELIVERED -> throw UnCancellableStatusException("이미 배송된 주문입니다.")
            CANCELED -> throw UnCancellableStatusException("이미 취소된 주문입니다.")
            null -> throw UnCancellableStatusException("주문 상태가 없습니다.")
        }

        return orderStore.cancelOrder(orderId)
    }

    override fun deliverySubscribe(orderId: Long, memberId: Long): Order {
        orderReader.subscribeDelivery(orderId, memberId)
        TODO("Not yet implemented")
    }
}
