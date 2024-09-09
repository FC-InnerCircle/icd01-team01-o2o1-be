package org.inner.circle.o2oserver.member.domain

interface ExternalUseCase {
    fun sendMemberData(member: Member): Boolean

    fun sendMemberInfo(memberDetail: MemberDetail, address: Address): Boolean

    fun sendDeleteMemberRequest(id: Long): Boolean

    fun sendCreateAddressRequest(address: Address): Boolean

    fun sendDeleteAddressRequest(addressId: Long): Boolean
}
