package org.inner.circle.o2oserver.order.application

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.inner.circle.o2oserver.order.domain.OrderUseCase
import org.inner.circle.o2oserver.order.presentation.dto.OrderDeliveryResponse
import org.inner.circle.o2oserver.order.presentation.dto.OrderDetailResponse
import org.inner.circle.o2oserver.order.presentation.dto.OrderListResponse
import org.springframework.stereotype.Component

@Component
class OrderQueryFacade(
    private val orderUseCase: OrderUseCase,
) {
    fun getOrderDetail(orderId: Long, userName: String): OrderDetailResponse.OrderInfoDetail {
        // get member
        val order = orderUseCase.getOrderDetail(orderId)
        return OrderDetailResponse.OrderInfoDetail.toResponse(order)
    }

    fun getOrderList(userName: String): OrderListResponse.OrderListResponse {
        // get member
        val memberId = 0L
        val orderList = orderUseCase.getOrderList(memberId)
        return OrderListResponse.OrderListResponse.toResponse(orderList)
    }

    fun deliverySubscribe(orderId: Long, username: String): Flow<OrderDeliveryResponse.OrderDelivery> =
        flow {
            val memberId = 1L
            orderUseCase.deliverySubscribe(orderId, memberId).map {
                emit(OrderDeliveryResponse.OrderDelivery.toResponse(it))
            }
        }

    fun orderStatusSubscribe(orderId: Long, username: String): Flow<OrderDetailResponse.OrderInfoDetail> =
        flow {
            val memberId = 1L
            orderUseCase.orderStatusSubscribe(orderId, memberId).map {
                emit(OrderDetailResponse.OrderInfoDetail.toResponse(it))
            }
        }
}
