package org.inner.circle.o2oserver.member.application

import org.inner.circle.o2oserver.member.domain.Address
import org.inner.circle.o2oserver.member.domain.ExternalUseCase
import org.inner.circle.o2oserver.member.domain.Member
import org.inner.circle.o2oserver.member.domain.MemberDetail
import org.inner.circle.o2oserver.member.domain.MemberUseCase
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MemberInfoFacade(
    private val memberUseCase: MemberUseCase,
    private val externalUseCase: ExternalUseCase,
) {
    fun getMemberInfo(id: String): Member {
        return memberUseCase.getMemberInfo(id)
    }

    @Transactional
    fun createMemberInfo(member: MemberDetail, address: Address) {
        memberUseCase.createMemberInfo(member, address)
        externalUseCase.sendMemberInfo(member, address)
    }

    @Transactional
    fun deleteMember(id: String) {
        val member = getMemberInfo(id)
        memberUseCase.deleteMember(id)
        externalUseCase.sendDeleteMemberRequest(member.memberId!!)
    }

    fun getAddresses(memberId: String): List<Address> {
        return memberUseCase.getAddresses(memberId)
    }

    @Transactional
    fun createAddress(address: Address): Address {
        val newAddress = memberUseCase.createAddress(address)
        externalUseCase.sendCreateAddressRequest(newAddress)
        return newAddress
    }

    fun setDefaultAddress(memberId: String, addressId: Long) {
        memberUseCase.setDefaultAddress(memberId, addressId)
    }

    @Transactional
    fun deleteAddress(addressId: Long) {
        memberUseCase.deleteAddress(addressId)
        externalUseCase.sendDeleteAddressRequest(addressId)
    }
}
