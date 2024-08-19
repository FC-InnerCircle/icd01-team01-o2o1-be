package org.inner.circle.o2oserver.member.application.service

import org.inner.circle.o2oserver.member.application.usecase.MemberInfoUsecase
import org.inner.circle.o2oserver.member.domain.Member
import org.springframework.stereotype.Service

@Service
class MemberInfoService : MemberInfoUsecase {
    override fun getMemberInfo(
        memberId: String,
        memberName: String
    ): Member {
        return Member()
    }
}
