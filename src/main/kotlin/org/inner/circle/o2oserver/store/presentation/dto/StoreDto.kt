package org.inner.circle.o2oserver.store.presentation.dto

import org.inner.circle.o2oserver.store.domain.Address
import org.inner.circle.o2oserver.store.domain.review.ReviewInfo

data class CommonResponse(
    val response: Any,
    val statusCode: Int,
    val msg: String,
)

data class CommonListResponse(
    val totalCount: Long,
    val page: Int?,
    val size: Int,
    val response: Any,
    val statusCode: Int,
    val msg: String,
)

data class StoreListRequest(
    val address: AddressDTO,
    val category: String?,
    val page: Int = 1,
    val size: Int = 10,
    val keyword: String = "",
)

data class AddressDTO(
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val addressDetail: String?,
    val zipCode: String,
) {
    fun toDomain(): Address {
        return Address(latitude, longitude, address, addressDetail, zipCode)
    }
}

data class StoreResponse(
    val stores: List<StoreInfo>,
    val totalCount: Int,
    val page: Int,
    val size: Int,
    val statusCode: Int,
    val msg: String,
)

data class StoreInfo(
    val storeId: Long,
    val storeName: String,
    val minimumPrice: Int,
    val deliveryPrice: Int,
    val reviewCount: Int,
    val reviewRate: Double,
    val thumbnailUrl: String,
    val category: String,
)

data class Review(
    val reviewId: Int,
    val contents: String,
    val rating: Int,
    val reviewImage: List<String>,
)

data class StoreReviewDTO(
    val storeName: String,
    val reviews: List<ReviewInfo>,
)
