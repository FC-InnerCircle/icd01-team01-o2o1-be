package org.inner.circle.o2oserver.order.domain

import org.inner.circle.o2oserver.commons.exception.Exceptions
import org.inner.circle.o2oserver.commons.exception.Exceptions.UnCancellableStatusException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderStore: OrderStore,
    private val orderReader: OrderReader,
    private val orderCaller: OrderCaller,
) : OrderUseCase {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Transactional
    override fun createOrder(order: Order): Order {
        val saveOrderId = orderCaller.saveOrderCall(order)
        log.info("주문 정보 저장 API 응답 orderId: $saveOrderId")
        return orderStore.saveOrder(order, saveOrderId)
    }

    override fun getOrderDetail(orderId: Long): Order {
        return orderReader.findOrderIsNullable(orderId) ?: let {
            val order = orderCaller.getOrderDetailCall(orderId)
            log.info("주문 정보 조회 API 응답 orderId: $orderId")
            orderStore.saveOrder(order, orderId)
        }
    }

    override fun getOrderList(memberId: Long): List<Order> {
        return orderReader.findOrdersByMember(memberId)
    }

    @Transactional
    override fun cancelOrder(orderId: Long, memberId: Long): Long {
        val order = orderReader.findOrder(orderId)

        if (order.memberId != memberId) {
            log.error("주문자가 아닙니다. memberId: $memberId, order.memberId: ${order.memberId}")
            throw Exceptions.BadRequestException("주문자가 아닙니다.")
        }

        if (order.orderStatus?.isStatusByThrowException() == false) {
            log.error("취소할 수 없는 주문입니다. orderId: $orderId")
            throw UnCancellableStatusException("취소할 수 없는 주문입니다.")
        }

        return orderStore.cancelOrder(orderId)
    }

    override fun createReviewOrder(review: Review): Review {
        val order = orderReader.findOrder(review.orderId)
        if (order.memberId != review.memberId) {
            log.error("주문자가 아닙니다. review.memberId: ${review.memberId}, order.memberId: ${order.memberId}")
            throw Exceptions.BadRequestException("주문자가 아닙니다.")
        }

//        order.orderStatus?.let {
//            if (!it.isStatus()) {
//                throw Exceptions.BadRequestException("주문 상태가 완료되지 않았습니다.")
//            }
//        }

//        val reviewCall = orderCaller.saveOrderReviewCall(review) // 2조 리뷰생성 api 일단 보류
        return orderStore.saveReview(review)
    }
}
