package org.inner.circle.o2oserver.order.application

import io.github.oshai.kotlinlogging.KotlinLogging
import org.inner.circle.o2oserver.order.domain.OrderUseCase
import org.inner.circle.o2oserver.order.presentation.dto.OrderCreateRequest
import org.inner.circle.o2oserver.order.presentation.dto.OrderCreateResponse
import org.springframework.stereotype.Component

@Component
class OrderCommandFacade(
    private val orderService: OrderUseCase
) {
    private val log = KotlinLogging.logger {}

    fun createOrder(
        orderCreate: OrderCreateRequest.OrderCreate
    ): OrderCreateResponse.OrderCreateResult {
        val toOrder = OrderCreateRequest.OrderCreate.toOrder(orderCreate)
        val createOrder = orderService.createOrder(toOrder)
        return OrderCreateResponse.OrderCreateResult.toDto(createOrder)
    }
}
