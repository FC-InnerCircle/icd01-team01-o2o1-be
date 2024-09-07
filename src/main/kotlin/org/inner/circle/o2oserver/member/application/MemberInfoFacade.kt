package org.inner.circle.o2oserver.member.application

import org.inner.circle.o2oserver.member.domain.Address
import org.inner.circle.o2oserver.member.domain.ExternalService
import org.inner.circle.o2oserver.member.domain.Member
import org.inner.circle.o2oserver.member.domain.MemberDetail
import org.inner.circle.o2oserver.member.domain.MemberService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MemberInfoFacade(
    private val memberService: MemberService,
    private val externalService: ExternalService,
) {
    fun getMemberInfo(id: String): Member {
        return memberService.getMemberInfo(id)
    }

    @Transactional
    fun createMemberInfo(member: MemberDetail, address: Address) {
        memberService.createMemberInfo(member, address)
        externalService.sendMemberInfo(member, address)
    }

    @Transactional
    fun deleteMember(id: String) {
        val member = getMemberInfo(id)
        memberService.deleteMember(id)
        externalService.sendDeleteMemberRequest(member.memberId!!)
    }

    fun getAddresses(memberId: String): List<Address> {
        return memberService.getAddresses(memberId)
    }

    @Transactional
    fun createAddress(address: Address): Address {
        val newAddress = memberService.createAddress(address)
        externalService.sendCreateAddressRequest(newAddress)
        return newAddress
    }

    fun setDefaultAddress(memberId: String, addressId: Long) {
        memberService.setDefaultAddress(memberId, addressId)
    }

    @Transactional
    fun deleteAddress(addressId: Long) {
        memberService.deleteAddress(addressId)
        externalService.sendDeleteAddressRequest(addressId)
    }
}
