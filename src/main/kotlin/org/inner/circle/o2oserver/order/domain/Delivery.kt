package org.inner.circle.o2oserver.order.domain

import org.inner.circle.o2oserver.commons.enums.DeliveryStatus
import java.time.LocalDateTime

class Delivery(
    val deliveryId: Long? = 0,
    val orderId: Long,
    val courierId: Long,
    val latitude: Double,
    val longitude: Double,
    val deliveryStatus: DeliveryStatus,
    val timestamp: LocalDateTime = LocalDateTime.now(),
) {



}
