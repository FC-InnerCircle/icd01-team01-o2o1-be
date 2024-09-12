package org.inner.circle.o2oserver.order.application

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.inner.circle.o2oserver.member.domain.MemberUseCase
import org.inner.circle.o2oserver.order.domain.OrderUseCase
import org.inner.circle.o2oserver.order.presentation.dto.OrderDeliveryResponse
import org.inner.circle.o2oserver.order.presentation.dto.OrderDetailResponse
import org.inner.circle.o2oserver.order.presentation.dto.OrderListResponse
import org.springframework.stereotype.Component

@Component
class OrderQueryFacade(
    private val orderUseCase: OrderUseCase,
    private val memberUseCase: MemberUseCase,
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

    fun deliverySubscribe(orderId: Long, username: String): Flow<OrderDeliveryResponse.OrderDelivery> =
        flow {
            val member = memberUseCase.getMemberInfo(username)
            orderUseCase.deliverySubscribe(orderId, member.memberId!!).map {
                emit(OrderDeliveryResponse.OrderDelivery.toResponse(it))
            }
        }

    fun orderStatusSubscribe(orderId: Long, username: String): Flow<OrderDetailResponse.OrderInfoDetail> =
        flow {
            val member = memberUseCase.getMemberInfo(username)
            orderUseCase.orderStatusSubscribe(orderId, member.memberId!!).map {
                emit(OrderDetailResponse.OrderInfoDetail.toResponse(it))
            }
        }
}
