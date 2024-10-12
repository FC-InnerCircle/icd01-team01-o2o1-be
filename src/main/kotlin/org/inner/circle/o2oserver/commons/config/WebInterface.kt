package org.inner.circle.o2oserver.commons.config

import org.inner.circle.o2oserver.order.infrastructure.dto.OrderClientRequest
import org.inner.circle.o2oserver.order.infrastructure.dto.OrderClientResponse
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange

@HttpExchange("/api")
interface WebInterface {

    @PostExchange("/v1/order")
    fun createOrder(@RequestBody order: OrderClientRequest.OrderCreate) : OrderClientResponse.OrderCreateResult

}
