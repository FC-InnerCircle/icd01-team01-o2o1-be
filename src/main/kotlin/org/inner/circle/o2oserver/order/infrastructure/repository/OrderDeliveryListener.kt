package org.inner.circle.o2oserver.order.infrastructure.repository

import com.mongodb.client.MongoClient
import com.mongodb.client.model.changestream.OperationType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Component

@Component
class OrderDeliveryListener(
    private val mongoClient: MongoClient,
) {
    fun watchDelivery(orderId: Long): Flow<DeliveryEntity> = flow {
        val database = mongoClient.getDatabase("o2o")
        val changeStream = database.getCollection("order").watch()
        changeStream.forEach { change ->
            if (change.operationType in listOf(OperationType.INSERT)) {
                if (change.documentKey?.get("orderId")?.asNumber()?.longValue() == orderId) {
                    emit(DeliveryEntity.toEntity(change.documentKey!!))
                }
            }
        }
    }
}
