package org.inner.circle.o2oserver.member.application.usecase

import org.inner.circle.o2oserver.member.domain.Member

interface MemberInfoUsecase {
    fun getMemberInfo(memberId: String, memberName: String): Member
}
