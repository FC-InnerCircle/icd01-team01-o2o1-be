package org.inner.circle.o2oserver.order.presentation.dto

import org.inner.circle.o2oserver.order.domain.Delivery

class OrderDeliveryResponse {

    data class DeliveryEvent(
        val id: Long,
        val event: String,
        val data: OrderDelivery,
    ) {
        companion object {
            fun toResponse(
                orderId: Long,
                orderDelivery: OrderDelivery
            ): DeliveryEvent {
                return DeliveryEvent(
                    id = orderId,
                    event = "deliveryLocationUpdate",
                    data = orderDelivery,
                )
            }
        }
    }

    data class OrderDelivery(
        val courierID: Long,
        val latitude: Double,
        val longitude: Double,
        val timestamp: String,
    ) {
        companion object {
            fun toResponse(delivery: Delivery): OrderDelivery {
                return OrderDelivery(
                    courierID = delivery.courierId,
                    latitude = delivery.latitude,
                    longitude = delivery.longitude,
                    timestamp = delivery.timestamp.toString(),
                )
            }
        }
    }
}
