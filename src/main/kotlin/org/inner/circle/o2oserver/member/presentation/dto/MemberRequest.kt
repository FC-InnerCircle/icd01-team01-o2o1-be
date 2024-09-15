package org.inner.circle.o2oserver.member.presentation.dto

import org.inner.circle.o2oserver.member.domain.Address
import org.inner.circle.o2oserver.member.domain.MemberDetail

class MemberRequest {
    data class MemberInfo(val nickName: String, val contact: String, val address: AddressRequest) {
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
            val address: String,
            val addressDetail: String,
            val latitude: Double,
            val longitude: Double,
            val zipCode: String,
        )
    }
}
