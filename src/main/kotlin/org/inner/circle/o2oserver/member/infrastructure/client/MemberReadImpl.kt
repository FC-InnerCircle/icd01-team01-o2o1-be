package org.inner.circle.o2oserver.member.infrastructure.client

import org.inner.circle.o2oserver.member.domain.MemberReader
import org.inner.circle.o2oserver.member.infrastructure.repository.MemberRepository
import org.springframework.stereotype.Component

@Component
class MemberReadImpl(
    private val memberRepository: MemberRepository
) : MemberReader {
    fun getMember() {
        //
    }
}
