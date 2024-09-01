package org.inner.circle.o2oserver.commons.config

import org.inner.circle.o2oserver.order.domain.Order
import org.springframework.stereotype.Component
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange

@Component
@HttpExchange
interface WebInterface {

    @PostExchange("/api/v1/order")
    fun createOrder(order: Order)

}
