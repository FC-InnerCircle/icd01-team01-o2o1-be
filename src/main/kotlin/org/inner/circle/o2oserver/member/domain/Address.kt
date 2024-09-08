package org.inner.circle.o2oserver.member.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "addresses")
class Address(
    @Id val addressId: Long? = 0,
    val memberId: String,
    val address: String,
    val detail: String,
    val latitude: Double,
    val longitude: Double,
    val zipCode: String,
    val isDefault: Boolean,
) {
    fun withDefaultStatus(isDefault: Boolean): Address {
        return Address(
            addressId = this.addressId,
            memberId = this.memberId,
            address = this.address,
            detail = this.detail,
            latitude = this.latitude,
            longitude = this.longitude,
            zipCode = this.zipCode,
            isDefault = isDefault,
        )
    }
}
