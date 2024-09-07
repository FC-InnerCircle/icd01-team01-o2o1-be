package org.inner.circle.o2oserver.member.presentation.dto

data class AddressResponse<T>(
    val response: T,
    val statusCode: Int,
    val msg: String,
)

data class GetAddressResponseData(
    val addresses: List<AddressResponseItem>,
)

data class AddressResponseItem(
    val addressId: Long,
    val address: String,
    val detail: String,
    val latitude: Double,
    val longitude: Double,
    val zipCode: String,
    val addressStatus: String,
)

data class PostAddressResponseData(
    val addressId: Long,
)
