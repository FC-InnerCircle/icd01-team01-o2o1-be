package org.inner.circle.o2oserver.member.domain

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "addresses")
data class Address(
    val addressId: Long? = 0,
    val memberId: Long,
    val address: String,
    val detail: String,
    val latitude: Double,
    val longitude: Double,
    val isDefault: Boolean,
)
