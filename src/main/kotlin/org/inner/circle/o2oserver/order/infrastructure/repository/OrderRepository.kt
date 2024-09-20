package org.inner.circle.o2oserver.order.infrastructure.repository

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : MongoRepository<OrderEntity, ObjectId> {
    fun findFirstByOrderByOrderId(orderId: Long): OrderEntity?

    fun findByMemberId(memberId: Long): List<OrderEntity>?

    fun findFirstByOrderByOrderIdDesc(): OrderEntity?
}
