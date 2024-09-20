package org.inner.circle.o2oserver.member.presentation.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.inner.circle.o2oserver.member.domain.Address
import org.inner.circle.o2oserver.member.domain.MemberDetail

class MemberRequest {
    data class MemberInfo(
        @field:NotBlank(message = "NickName cannot be blank") val nickName: String,
        @field:NotBlank(message = "Contact cannot be blank") @field:Pattern(
            regexp = "\\d{2,3}-?\\d{3,4}-?\\d{4}",
            message = "Contact must be a valid format (e.g., 01012345678, 010-123-4567, 011-2345-6789)",
        ) val contact: String,
        @field:NotNull(message = "Address cannot be null") @field:Valid val address: AddressRequest,
    ) {
        companion object {
            fun toMemberDetail(memberInfo: MemberInfo, memberId: String): MemberDetail {
                return MemberDetail(
                    id = memberId,
                    nickName = memberInfo.nickName,
                    contact = memberInfo.contact,
                )
            }

            fun toAddress(memberInfo: MemberInfo, memberId: String): Address {
                return Address(
                    memberId = memberId,
                    address = memberInfo.address.address,
                    addressDetail = memberInfo.address.addressDetail,
                    latitude = memberInfo.address.latitude,
                    longitude = memberInfo.address.longitude,
                    zipCode = memberInfo.address.zipCode,
                    isDefault = true, // 기본 주소로 설정
                )
            }
        }

        data class AddressRequest(
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
        )
    }
}
