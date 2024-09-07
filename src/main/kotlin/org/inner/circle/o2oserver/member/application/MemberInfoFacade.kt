package org.inner.circle.o2oserver.member.application

import org.inner.circle.o2oserver.member.domain.Address
import org.inner.circle.o2oserver.member.domain.Member
import org.inner.circle.o2oserver.member.domain.MemberDetail
import org.inner.circle.o2oserver.member.domain.MemberService
import org.springframework.stereotype.Component

@Component
class MemberInfoFacade(
    private val memberService: MemberService,
) {
    fun getMemberInfo(id: String): Member {
        return memberService.getMemberInfo(id)
    }

    fun createMemberInfo(member: MemberDetail, address: Address) {
        memberService.createMemberInfo(member, address)
    }

    fun deleteMember(id: String) {
        memberService.deleteMember(id)
    }

    fun getAddresses(memberId: String): List<Address> {
        return memberService.getAddresses(memberId)
    }

    fun createAddress(address: Address): Address {
        return memberService.createAddress(address)
    }

    fun setDefaultAddress(memberId: String, addressId: Long) {
        memberService.setDefaultAddress(memberId, addressId)
    }

    fun deleteAddress(addressId: Long) {
        memberService.deleteAddress(addressId)
    }
}
