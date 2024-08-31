package org.inner.circle.o2oserver.order.domain

class Address(
    val addressId: Long,
    val address: String,
    val detail: String,
    val zipCode: String,
    val latitude: Double,
    val longitude: Double,
    val isDefault: Boolean
)
