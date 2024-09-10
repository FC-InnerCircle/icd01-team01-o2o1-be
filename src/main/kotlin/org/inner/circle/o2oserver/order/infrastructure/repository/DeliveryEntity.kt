package org.inner.circle.o2oserver.order.infrastructure.repository

import org.bson.BsonDocument
import org.bson.types.ObjectId
import org.inner.circle.o2oserver.commons.enums.DeliveryStatus
import org.inner.circle.o2oserver.order.domain.Delivery
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Document(collection = "delivery")
class DeliveryEntity(
    @Id
    val id: ObjectId = ObjectId.get(),
    val orderId: Long,
    val courierId: Long,
    val latitude: Double,
    val longitude: Double,
    val deliveryStatus: DeliveryStatus,
    val createdAt: LocalDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul")),
    val updatedAt: LocalDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul")),
) {
    companion object {
        fun toDomain(deliveryEntity: DeliveryEntity): Delivery {
            return Delivery(
                orderId = deliveryEntity.orderId,
                courierId = deliveryEntity.courierId,
                latitude = deliveryEntity.latitude,
                longitude = deliveryEntity.longitude,
                deliveryStatus = deliveryEntity.deliveryStatus,
                timestamp = deliveryEntity.createdAt,
            )
        }

        fun watchToEntity(document: BsonDocument): DeliveryEntity {
            return DeliveryEntity(
                orderId = document.getInt64("orderId")?.longValue() ?: 0,
                courierId = document.getInt64("courierId")?.longValue() ?: 0,
                latitude = document.getDouble("latitude")?.value ?: 0.0,
                longitude = document.getDouble("longitude")?.value ?: 0.0,
                deliveryStatus = DeliveryStatus.valueOf(document.getString("deliveryStatus").value),
                createdAt = getLongToDateTime(document.getInt64("createdAt").value),
                updatedAt = getLongToDateTime(document.getInt64("updatedAt").value),
            )
        }

        private fun getLongToDateTime(dateTime: Long): LocalDateTime {
            val zoneId = ZoneId.of("Asia/Seoul")
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTime), zoneId)
        }
    }
}
