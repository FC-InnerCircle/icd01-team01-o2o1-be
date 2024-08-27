package org.inner.circle.o2oserver.order.presentation.dto

import org.inner.circle.o2oserver.order.domain.Order

class OrderCreateResponse {

    data class OrderCreateResult(
        private val orderId: Long,
        private val storeId: Long,
    ) {
        companion object {
            fun toDto(
                order: Order
            ): OrderCreateResult {
                return OrderCreateResult(
                    orderId = order.orderId ?: 0,
                    storeId = order.storeId ?: 0
                )
                TODO("응답 객체에 대한 핸들링 수정필요")
            }
        }
    }
}
