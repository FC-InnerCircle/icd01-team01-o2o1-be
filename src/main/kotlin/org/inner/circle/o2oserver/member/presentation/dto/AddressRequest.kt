package org.inner.circle.o2oserver.member.presentation.dto

import org.inner.circle.o2oserver.member.domain.Address

class AddressRequest {
    data class CreateAddress(
        val address: String,
        val addressDetail: String,
        val latitude: Double,
        val longitude: Double,
        val zipCode: String,
    ) {
        companion object {
            fun toAddress(createAddress: CreateAddress, memberId: String): Address {
                return Address(
                    memberId = memberId,
                    address = createAddress.address,
                    addressDetail = createAddress.addressDetail,
                    latitude = createAddress.latitude,
                    longitude = createAddress.longitude,
                    zipCode = createAddress.zipCode,
                    isDefault = false,
                )
            }
        }
    }
}
