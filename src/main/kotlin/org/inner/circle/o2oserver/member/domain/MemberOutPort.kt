package org.inner.circle.o2oserver.member.domain

interface MemberOutPort {
    fun sendMemberData(member: Member): Boolean

    fun sendMemberInfo(memberDetail: MemberDetail, address: Address): Boolean
}
