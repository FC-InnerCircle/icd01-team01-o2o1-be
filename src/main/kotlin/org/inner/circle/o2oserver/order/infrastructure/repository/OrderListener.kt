package org.inner.circle.o2oserver.order.infrastructure.repository

import com.mongodb.client.MongoClient
import com.mongodb.client.model.changestream.OperationType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.springframework.stereotype.Component

@Component
class OrderListener(
    private val mongoClient: MongoClient,
) {
    fun subscribeDelivery(
        orderId: Long
    ): Flow<DeliveryEntity> = flow {
        val database = mongoClient.getDatabase("o2o")
        val changeStream = database.getCollection("delivery").watch()
        changeStream.forEach { change ->
            if (change.operationType in listOf(OperationType.INSERT)) {
                if (change.documentKey?.get("orderId")?.asNumber()?.longValue() == orderId) {
                    emit(DeliveryEntity.watchToEntity(change.documentKey!!))
                }
            }
        }
    }

    // todo : spring event 활용해보기
    fun subscribeOrderStatus(
        orderId: Long
    ): Flow<OrderStatusEntity> = flow {
        val database = mongoClient.getDatabase("o2o")
        val changeStream = database.getCollection("order").watch()
        changeStream.forEach { change ->
            if (change.operationType in listOf(OperationType.INSERT)) {
                if (change.documentKey?.get("orderId")?.asNumber()?.longValue() == orderId) {
                    emit(OrderStatusEntity.watchToEntity(change.documentKey!!))
                }
            }
        }
    }
}
