package org.inner.circle.o2oserver.store.infrastructure.client

import org.inner.circle.o2oserver.store.domain.store.Store
import org.inner.circle.o2oserver.store.domain.store.StoreReader
import org.inner.circle.o2oserver.store.domain.store.command.StoreListCommand
import org.springframework.stereotype.Component

@Component
class StoreReadImpl(private val storeApiClient: StoreApiClient) : StoreReader {
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
            thumbnails = listOf("www.google.com")
        )
    }

    override fun getStoreList(command: StoreListCommand): Pair<List<Store>, Int> {
        val response = storeApiClient.getStoreList()
        return Pair(
            response.stores.map { it.toDomain() },
            response.totalCount
        )
    }
}
