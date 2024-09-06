package org.inner.circle.o2oserver.member.domain

interface MemberUseCase {
    fun getMemberInfo(id: String): Member
}
