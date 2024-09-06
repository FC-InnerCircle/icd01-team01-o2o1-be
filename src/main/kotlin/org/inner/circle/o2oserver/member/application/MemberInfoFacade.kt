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
}
