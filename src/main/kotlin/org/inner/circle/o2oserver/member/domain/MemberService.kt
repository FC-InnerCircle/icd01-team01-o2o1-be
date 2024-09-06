package org.inner.circle.o2oserver.member.domain

import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberReader: MemberReader,
) : MemberUseCase {
    override fun getMemberInfo(id: String): Member {
        return memberReader.getMemberById(id)
    }
}
