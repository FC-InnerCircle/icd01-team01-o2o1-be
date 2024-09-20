package org.inner.circle.o2oserver.member.presentation.dto

import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.inner.circle.o2oserver.member.domain.Address

class AddressRequest {
    data class CreateAddress(
        @field:NotBlank(message = "Address cannot be blank") val address: String,
        @field:NotBlank(message = "Address detail cannot be blank") val addressDetail: String,
        @field:DecimalMin(
            value = "33.0",
            inclusive = true,
            message = "Latitude must be at least 33.0",
        ) @field:DecimalMax(value = "39.0", inclusive = true, message = "Latitude must be at most 39.0") val latitude: Double,
        @field:DecimalMin(
            value = "124.0",
            inclusive = true,
            message = "Longitude must be at least 124.0",
        ) @field:DecimalMax(value = "132.0", inclusive = true, message = "Longitude must be at most 132.0") val longitude: Double,
        @field:NotBlank(message = "Zip code cannot be blank") @field:Pattern(
            regexp = "\\d{5}(?:-\\d{4})?",
            message = "ZipCode must be a valid format (e.g., 12345 or 12345-6789)",
        ) val zipCode: String,
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
