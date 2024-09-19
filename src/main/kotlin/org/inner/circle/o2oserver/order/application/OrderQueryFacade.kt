package org.inner.circle.o2oserver.order.application

import org.inner.circle.o2oserver.member.domain.MemberUseCase
import org.inner.circle.o2oserver.order.domain.OrderUseCase
import org.inner.circle.o2oserver.order.presentation.dto.OrderDetailResponse
import org.inner.circle.o2oserver.order.presentation.dto.OrderListResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Component
class OrderQueryFacade(
    private val orderUseCase: OrderUseCase,
    private val memberUseCase: MemberUseCase,
    private val orderListenerFacade: OrderListenerFacade,
    private val deliveryListenerFacade: DeliveryListenerFacade,
) {
    fun getOrderDetail(orderId: Long, userName: String): OrderDetailResponse.OrderInfoDetail {
        val member = memberUseCase.getMemberInfo(userName)
        val order = orderUseCase.getOrderDetail(orderId)
        return OrderDetailResponse.OrderInfoDetail.toResponse(order)
    }

    fun getOrderList(userName: String): OrderListResponse.OrderListResponse {
        val member = memberUseCase.getMemberInfo(userName)
        val orderList = orderUseCase.getOrderList(member.memberId!!)
        return OrderListResponse.OrderListResponse.toResponse(orderList)
    }

    fun deliverySubscribe(orderId: Long, username: String): SseEmitter {
        val member = memberUseCase.getMemberInfo(username)
        return deliveryListenerFacade.deliverySubscribe(orderId)
    }

    fun orderStatusSubscribe(orderId: Long, username: String): SseEmitter {
        val member = memberUseCase.getMemberInfo(username)
        return orderListenerFacade.orderStatusSubscribe(orderId)
    }
}
