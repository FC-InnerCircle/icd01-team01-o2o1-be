package org.inner.circle.o2oserver.order.presentation.dto

import org.inner.circle.o2oserver.order.domain.Delivery
import org.inner.circle.o2oserver.order.domain.Order

class OrderDeliveryResponse {

    data class OrderDelivery(
        val courierID: Long,
        val latitude: Double,
        val longitude: Double,
        val timestamp: String
    ) {
        companion object {
            fun toResponse(delivery: Delivery): OrderDelivery {
                return OrderDelivery(
                    courierID = delivery.courierId,
                    latitude = delivery.latitude,
                    longitude = delivery.longitude,
                    timestamp = delivery.timestamp.toString()
                )
            }
        }
    }
}
