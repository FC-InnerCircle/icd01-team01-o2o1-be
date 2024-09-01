package org.inner.circle.o2oserver.member.domain

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LoginService(
    private val memberReader: MemberReader,
    private val memberStore: MemberStore,
    private val memberOutPort: MemberOutPort
) : LoginUseCase {

    override fun findOrCreateMember(member: Member): MemberCreationResult {
        val existingMember = memberReader.findBySnsTypeAndSubId(member.snsType, member.subId)
        return if (existingMember != null) {
            MemberCreationResult(existingMember, false)
        } else {
            val newMember = memberStore.save(member)

            try {
                memberOutPort.sendMemberData(newMember)
            } catch (e: Exception) {
                // 외부 서버와 전송 실패시 DB에서 정보 삭제
                memberStore.delete(newMember)
                throw RuntimeException(
                    "External server communication failed, rolling back transaction",
                    e
                )
            }
            MemberCreationResult(newMember, true)
        }
    }
}


