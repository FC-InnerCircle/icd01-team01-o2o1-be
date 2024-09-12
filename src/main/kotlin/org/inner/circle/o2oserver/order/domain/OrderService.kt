package org.inner.circle.o2oserver.order.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.inner.circle.o2oserver.commons.exception.Exceptions
import org.inner.circle.o2oserver.commons.exception.Exceptions.UnCancellableStatusException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderStore: OrderStore,
    private val orderReader: OrderReader,
//    private val orderCaller: OrderCaller,
) : OrderUseCase {
    @Transactional
    override fun createOrder(order: Order): Order {
        // todo : save and get order id from api
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

    @Transactional
    override fun cancelOrder(orderId: Long, memberId: Long): Order {
        val order = orderReader.findOrderDetailByOrderId(orderId)

        if (order.memberId != memberId) {
            throw Exceptions.BadRequestException("주문자가 아닙니다.")
        }

        if (order.orderStatus?.isStatusByThrowException() == false) {
            throw UnCancellableStatusException("취소할 수 없는 주문입니다.")
        }

        return orderStore.cancelOrder(orderId)
    }

    override fun deliverySubscribe(orderId: Long, memberId: Long): Flow<Delivery> =
        flow {
            orderReader.subscribeDelivery(orderId, memberId).let {
                it.collect { delivery ->
                    emit(delivery)
                }
            }
        }

    override fun orderStatusSubscribe(orderId: Long, memberId: Long): Flow<Order> =
        flow {
            orderReader.subscribeOrder(orderId, memberId).let {
                it.collect { order ->
                    emit(order)
                }
            }
        }

    override fun createReviewOrder(review: Review): Review {
        val order = orderReader.findOrderDetailByOrderId(review.orderId)
        if (order.memberId != review.memberId) {
            throw Exceptions.BadRequestException("주문자가 아닙니다.")
        }

//        order.orderStatus?.let {
//            if (!it.isStatus()) {
//                throw Exceptions.BadRequestException("주문 상태가 완료되지 않았습니다.")
//            }
//        }

        // todo : save and get order id from api
        //    private val orderCaller: OrderCaller,
        return orderStore.saveReview(review)
    }
}
