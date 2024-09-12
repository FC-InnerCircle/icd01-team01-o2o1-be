package org.inner.circle.o2oserver.order.presentation.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.inner.circle.o2oserver.commons.response.BaseResponse
import org.inner.circle.o2oserver.order.application.OrderQueryFacade
import org.inner.circle.o2oserver.order.presentation.dto.OrderDeliveryResponse
import org.inner.circle.o2oserver.order.presentation.dto.OrderDetailResponse
import org.inner.circle.o2oserver.order.presentation.dto.OrderListResponse
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/order")
class OrderQueryController(
    private val orderQueryFacade: OrderQueryFacade,
) : OrderQueryDoc {
    @GetMapping
    override fun getOrders(
        @AuthenticationPrincipal userDetails: UserDetails,
    ): BaseResponse<OrderListResponse.OrderListResponse> {
        val orderList = orderQueryFacade.getOrderList(userDetails.username)
        return BaseResponse.success(orderList)
    }

    @GetMapping("/{orderId}")
    override fun getOrder(
        @PathVariable orderId: Long,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): BaseResponse<OrderDetailResponse.OrderInfoDetail> {
        val orderDetail = orderQueryFacade.getOrderDetail(orderId, userDetails.username)
        return BaseResponse.success(orderDetail)
    }

    @GetMapping("/{orderId}/delivery/location", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    override fun deliverySubscribe(
        @PathVariable orderId: Long,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): Flow<BaseResponse<OrderDeliveryResponse.OrderDelivery>> =
        flow {
            val cancelOrderResult = orderQueryFacade.deliverySubscribe(orderId, userDetails.username)
            cancelOrderResult.map {
                emit(BaseResponse.success(it))
            }
        }

    @GetMapping("/status/{orderId}", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun orderStatusSubscribe(
        @PathVariable orderId: Long,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): Flow<BaseResponse<OrderDetailResponse.OrderInfoDetail>> =
        flow {
            val orderStatusResult = orderQueryFacade.orderStatusSubscribe(orderId, userDetails.username)
            orderStatusResult.map {
                emit(BaseResponse.success(it))
            }
        }
}
