package org.inner.circle.o2oserver.member.application

import org.inner.circle.o2oserver.member.domain.Member
import org.springframework.stereotype.Service

@Service
class MemberInfoService {
    fun getMemberInfo(memberId: String, memberName: String): Member? {
        return null
    }
}
