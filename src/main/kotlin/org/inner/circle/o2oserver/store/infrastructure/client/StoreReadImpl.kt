package org.inner.circle.o2oserver.store.infrastructure.client

import org.inner.circle.o2oserver.store.domain.store.Store
import org.inner.circle.o2oserver.store.domain.store.StoreListInfo
import org.inner.circle.o2oserver.store.domain.store.StoreReader
import org.inner.circle.o2oserver.store.domain.store.command.StoreListCommand
import org.inner.circle.o2oserver.store.infrastructure.repository.mongo.MongoRepository
import org.springframework.data.geo.Distance
import org.springframework.data.geo.Metrics
import org.springframework.data.geo.Point
import org.springframework.stereotype.Component

@Component
class StoreReadImpl(private val storeApiClient: StoreApiClient, private val mongoRepository: MongoRepository) : StoreReader {
    override fun getStoreDetail(storeId: Long): Store {
        // ## TODO 추후 API 로 구현 예정
        return Store(
            storeId = 1,
            storeName = "홍길동 한식당",
            contactNumber = "02-1234-5678",
            zipCode = "12345",
            address = "경기도성남시수정구",
            addressDetail = "1312호",
            latitude = 37.6,
            longitude = 126.8,
            openTime = "09:00",
            closeTime = "18:00",
            category = "한식",
            deliveryArea = "위례",
            minimumPrice = 15000,
            reviewCount = 10,
            reviewRate = 4.5,
            thumbnails = listOf("www.google.com"),
        )
    }

    override fun getStoreList(command: StoreListCommand): StoreListInfo {
        val response = storeApiClient.getStoreList()
        return StoreListInfo(totalCount = response.totalCount, stores = response.stores.map { it.toDomain() })
    }

    override fun getStoreListWithLocationAndName(command: StoreListCommand): StoreListInfo {
        val keyword = command.keyword ?: ""
        val (latitude, longitude) = command.address

        val point = Point(longitude, latitude)
        val distance = Distance(5.0, Metrics.KILOMETERS)
        val mongo = mongoRepository.findByStoreNameContainingAndLocationNear(keyword, point, distance)
        return StoreListInfo(totalCount = mongo.size, stores = mongo.map { it.toDomain() })
    }
}
