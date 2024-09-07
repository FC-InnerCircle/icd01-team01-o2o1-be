package org.inner.circle.o2oserver.member.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "addresses")
data class Address(
    @Id val addressId: Long? = 0,
    val memberId: String,
    val address: String,
    val detail: String,
    val latitude: Double,
    val longitude: Double,
    val zipCode: String,
    val isDefault: Boolean,
)
