package org.inner.circle.o2oserver.member.infrastructure.client

import org.inner.circle.o2oserver.member.domain.Member
import org.inner.circle.o2oserver.member.domain.MemberStore
import org.inner.circle.o2oserver.member.infrastructure.repository.MemberRepository
import org.springframework.stereotype.Component

@Component
class MemberStoreImpl(
    private val memberRepository: MemberRepository
) : MemberStore {

    override fun save(member: Member): Member {
        return memberRepository.save(member)
    }

    override fun delete(member: Member) {
        memberRepository.delete(member)
    }
}
