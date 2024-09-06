package org.inner.circle.o2oserver.member.infrastructure.client

import org.inner.circle.o2oserver.member.domain.Address
import org.inner.circle.o2oserver.member.domain.Member
import org.inner.circle.o2oserver.member.domain.MemberDetail
import org.inner.circle.o2oserver.member.domain.MemberOutPort
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class MemberApiClient() : MemberOutPort {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun sendMemberData(member: Member): Boolean {
        // 2팀 서버에 멤버 데이터 전송 결과에 따라 롤백 처리해야 함 (통신 미구현)
        // 우선 true로 처리
        return true
    }

    override fun sendMemberInfo(memberDetail: MemberDetail, address: Address): Boolean {
        // 2팀 서버에 멤버 정보 전송 결과에 따라 롤백 처리해야 함 (통신 미구현)
        // 우선 true로 처리
        return true
    }

    override fun sendDeleteMemberRequest(memberId: Long): Boolean {
        // 2팀 서버에 멤버 삭제 데이터 전송 결과에 따라 롤백 처리해야 함 (통신 미구현)
        // 우선 true로 처리
        return true
    }
}
