package org.inner.circle.o2oserver.member.presentation.dto

data class GetAddressResponse(
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

data class AddressIdResponse(
    val addressId: Long
)
