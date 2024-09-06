package org.inner.circle.o2oserver.member.domain

interface MemberUseCase {
    fun getMemberInfo(id: String): Member

    fun createMemberInfo(memberDetail: MemberDetail, address: Address)

    fun updateMemberInfo(id: String, memberDetail: MemberDetail)

    fun deleteMember(id: String)
}
