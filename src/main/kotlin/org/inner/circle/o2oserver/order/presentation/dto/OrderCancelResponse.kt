package org.inner.circle.o2oserver.order.presentation.dto

class OrderCancelResponse {

    data class OrderCancel(
        val orderId: Long
    ) {
        companion object {
            fun toResponse(orderId: Long): OrderCancel {
                return OrderCancel(
                    orderId = orderId
                )
            }
        }
    }
}
