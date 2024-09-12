package org.inner.circle.o2oserver.store.infrastructure.dto

import org.inner.circle.o2oserver.store.domain.store.Store

// ## TODO 가게 정보 리스트 조회 DTO
data class StoreRequest(
    val address: Address,
    val category: String?,
    val page: Int?,
    val size: Int?,
    val keyword: String?,
)

data class Address(
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val addressDetail: String?,
    val zipCode: String,
)

data class StoreListResponse(
    val stores: List<BriefStoreInfo>,
    val totalCount: Long,
    val page: Int,
    val size: Int,
    val statusCode: Int,
    val msg: String,
)

data class BriefStoreInfo(
    val storeId: Long,
    val storeName: String,
    val minimumPrice: Int,
    val deliveryPrice: Int,
    val reviewCount: Int,
    val reviewRate: Double,
    val thumbnailUrl: String,
    val category: String,
) {
    fun toDomain(): Store {
        return Store(
            storeId = storeId,
            storeName = storeName,
            minimumPrice = minimumPrice,
            deliveryPrice = deliveryPrice,
            reviewCount = reviewCount,
            thumbnails = listOf(thumbnailUrl),
            category = category,
            menus = listOf()
        )
    }
}

// ## TODO API 구현 시 해당 내용 사용 예정, 현재는 MOCKING
data class StoreResponse(
    val storeId: Long,
    val storeName: String,
    val contactNumber: String,
    val zipCode: String,
    val address: String,
    val addressDetail: String,
    val latitude: Double,
    val longitude: Double,
    val openTime: String,
    val closeTime: String,
    val category: String,
    val deliveryArea: String,
    val minimumPrice: Int,
    val reviewCount: Int,
    val reviewRate: Double,
    val thumbnails: List<String>,
) {
    fun toDomain(): Store {
        return Store(
            storeId = storeId,
            storeName = storeName,
            contactNumber = contactNumber,
            zipCode = zipCode,
            address = address,
            addressDetail = addressDetail,
            latitude = latitude,
            longitude = longitude,
            openTime = openTime,
            closeTime = closeTime,
            category = category,
            deliveryArea = deliveryArea,
            minimumPrice = minimumPrice,
            reviewCount = reviewCount,
            reviewRate = reviewRate,
            thumbnails = thumbnails,
            menus = listOf()
        )
    }
}
