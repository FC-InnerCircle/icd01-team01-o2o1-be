package org.inner.circle.o2oserver.member.domain

data class Address(
    val addressId: Long? = null,
    val address: String,
    val detailAddress: String,
    val latitude: Float,
    val longitude: Float,
)
