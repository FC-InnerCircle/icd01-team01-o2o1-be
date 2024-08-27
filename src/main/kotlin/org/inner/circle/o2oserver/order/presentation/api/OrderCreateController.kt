package org.inner.circle.o2oserver.order.presentation.api

import io.github.oshai.kotlinlogging.KotlinLogging
import org.inner.circle.o2oserver.order.application.OrderCommandFacade
import org.inner.circle.o2oserver.order.presentation.dto.OrderCreateRequest
import org.inner.circle.o2oserver.order.presentation.dto.OrderCreateResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 스웨거 생성을 위해 앞단에 interface 를
 * 두고 거기에 api doc 어노테이션을 만들 생각입니다.
 */
@RestController
@RequestMapping("/api/v1/orders")
class OrderCreateController(
    private val orderCommandFacade: OrderCommandFacade,
) {
    private val log = KotlinLogging.logger {}

    @PostMapping
    fun createOrder(
        orderCreate: OrderCreateRequest.OrderCreate
    ): OrderCreateResponse.OrderCreateResult {
        log.info { "order 생성 요청" }
        return orderCommandFacade.createOrder(orderCreate)
        TODO("공통 response 객체 만들어야함")
    }
}
