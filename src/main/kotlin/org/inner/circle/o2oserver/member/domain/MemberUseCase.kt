package org.inner.circle.o2oserver.member.domain

interface MemberUseCase {
    fun getMemberInfo(id: String): Member

    fun createMemberInfo(memberDetail: MemberDetail, address: Address)

    fun updateMemberInfo(id: String, memberDetail: MemberDetail)

    fun deleteMember(id: String)

    fun getAddresses(memberId: String): List<Address>

    fun createAddress(address: Address): Address

    fun setDefaultAddress(memberId: String, addressId: Long)

    fun deleteAddress(addressId: Long): Long
}
