package org.inner.circle.o2oserver.member.application.service

import org.inner.circle.o2oserver.member.application.usecase.MemberInfoUseCase
import org.inner.circle.o2oserver.member.domain.Member
import org.springframework.stereotype.Service

@Service
class MemberInfoService : MemberInfoUseCase {
    override fun getMemberInfo(
        memberId: String,
        memberName: String
    ): Member {
        return Member()
    }
}
