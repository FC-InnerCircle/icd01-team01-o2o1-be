package org.inner.circle.o2oserver.member.presentation.dto

import org.inner.circle.o2oserver.member.domain.Address
import org.inner.circle.o2oserver.member.domain.MemberDetail

class MemberRequest {
    data class MemberInfo(val nickName: String, val contact: String, val address: AddressRequest) {
        companion object {
            fun toMemberDetail(memberInfo: MemberInfo): MemberDetail {
                return MemberDetail(
                    nickName = memberInfo.nickName,
                    contact = memberInfo.contact,
                )
            }

            fun toAddress(memberInfo: MemberInfo, memberId: Long): Address {
                return Address(
                    memberId = memberId,
                    address = memberInfo.address.address,
                    detail = memberInfo.address.detailAddress,
                    latitude = memberInfo.address.latitude,
                    longitude = memberInfo.address.longitude,
                    isDefault = true, // 기본 주소로 설정
                )
            }
        }

        data class AddressRequest(
            val address: String,
            val detailAddress: String,
            val latitude: Double,
            val longitude: Double,
        )
    }
}
