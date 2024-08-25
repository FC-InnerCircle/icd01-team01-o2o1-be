package org.inner.circle.o2oserver.store.presentation.dto

data class CommonResponse<T>(
    val response: T,
    val statusCode: Int,
    val msg: String,
)

data class StoreListRequest(
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
