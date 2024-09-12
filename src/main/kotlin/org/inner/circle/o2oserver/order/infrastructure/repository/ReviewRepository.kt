package org.inner.circle.o2oserver.order.infrastructure.repository

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : MongoRepository<ReviewEntity, ObjectId> {
    fun findByOrderId(orderId: Long): ReviewEntity?
}
