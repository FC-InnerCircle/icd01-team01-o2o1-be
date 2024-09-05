package org.inner.circle.o2oserver.member.application

import org.inner.circle.o2oserver.member.domain.Address
import org.inner.circle.o2oserver.member.domain.MemberDetail
import org.springframework.stereotype.Component

@Component
class MemberInfoFacade {
    fun getMemberInfo(memberDetail: MemberDetail, address: Address): String? {
        return null
    }
}
