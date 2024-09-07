package org.inner.circle.o2oserver.member.infrastructure.client

import org.inner.circle.o2oserver.member.domain.Member
import org.inner.circle.o2oserver.member.domain.MemberReader
import org.inner.circle.o2oserver.member.infrastructure.repository.MemberRepository
import org.springframework.stereotype.Component

@Component
class MemberReaderImpl(
    private val memberRepository: MemberRepository,
) : MemberReader {
    override fun getMemberById(id: String): Member {
        return memberRepository.findById(id).orElseThrow {
            throw IllegalArgumentException("Member not found")
        }
    }

    override fun findBySnsTypeAndSubId(snsType: String, subId: String): Member? {
        return memberRepository.findBySnsTypeAndSubId(snsType, subId)
    }
}
