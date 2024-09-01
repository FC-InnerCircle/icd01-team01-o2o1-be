package org.inner.circle.o2oserver.order.application

import org.inner.circle.o2oserver.order.domain.OrderUseCase
import org.inner.circle.o2oserver.order.presentation.dto.OrderDetailResponse
import org.inner.circle.o2oserver.order.presentation.dto.OrderListResponse
import org.springframework.stereotype.Component

@Component
class OrderQueryFacade(
    private val orderService: OrderUseCase
) {
    fun getOrderDetail(
        orderId: Long,
        userName: String
    ): OrderDetailResponse.OrderInfoDetail {
        //get member
        val order = orderService.getOrderDetail(orderId)
        return OrderDetailResponse.OrderInfoDetail.toResponse(order)
    }

    fun getOrderList(
        userName: String
    ): OrderListResponse.OrderListResponse {
        //get member
        val memberId = 0L
        val orderList = orderService.getOrderList(memberId)
        return OrderListResponse.OrderListResponse.toResponse(orderList)
    }
}
