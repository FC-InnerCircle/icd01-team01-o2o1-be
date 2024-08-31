package org.inner.circle.o2oserver.order.presentation.api

import org.inner.circle.o2oserver.commons.models.BaseResponse
import org.inner.circle.o2oserver.order.application.OrderQueryFacade
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/order")
class OrderQueryController(
    private val orderQueryFacade: OrderQueryFacade
) {
    @GetMapping
    fun getOrders() {
        TODO()
    }

    @GetMapping("/{orderId}")
    fun getOrder(
        @PathVariable orderId: Long
    ): BaseResponse {
        val orderDetail = orderQueryFacade.getOrderDetail(orderId)
        return BaseResponse(
            response = orderDetail,
            statusCode = 200,
            msg = "success"
        )
    }
}
