package org.inner.circle.o2oserver.member.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "addresses")
class Address(
    @Id val addressId: Long? = 0,
    val memberId: String,
    val address: String,
    val addressDetail: String,
    val latitude: Double,
    val longitude: Double,
    val zipCode: String,
    val isDefault: Boolean,
) {
    fun updateDefaultStatus(isDefault: Boolean): Address {
        return Address(
            addressId = this.addressId,
            memberId = this.memberId,
            address = this.address,
            addressDetail = this.addressDetail,
            latitude = this.latitude,
            longitude = this.longitude,
            zipCode = this.zipCode,
            isDefault = isDefault,
        )
    }
}
