package org.inner.circle.o2oserver.store.infrastructure.repository.mongo

import org.springframework.data.geo.Distance
import org.springframework.data.geo.Point
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MongoRepository : MongoRepository<Store, String> {
    fun findByStoreNameContainingAndLocationNear(storeName: String, location: Point, distance: Distance): List<Store>
}
