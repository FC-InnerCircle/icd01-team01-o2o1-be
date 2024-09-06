package org.inner.circle.o2oserver.member.domain

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LoginService(
    private val memberReader: MemberReader,
    private val memberStore: MemberStore,
    private val memberOutPort: MemberOutPort,
    private val sequenceGenerator: SequenceGenerator,
) : LoginUseCase {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Transactional
    override fun findOrCreateMember(member: Member): MemberCreationResult {
        val existingMember = memberReader.findBySnsTypeAndSubId(member.snsType, member.subId)

        return if (existingMember != null) {
            MemberCreationResult(existingMember, false)
        } else {
            val memberId = sequenceGenerator.generate("memberSequence")

            val memberInfo = Member(
                memberId = memberId,
                name = member.name,
                snsType = member.snsType,
                subId = member.subId,
            )
            val newMember = memberStore.save(memberInfo)
            log.info("New member created with ID: ${newMember.id}")

            try {
                memberOutPort.sendMemberData(newMember)
            } catch (e: Exception) {
                throw RuntimeException(
                    "External server communication failed, rolling back transaction",
                    e,
                )
            }
            MemberCreationResult(newMember, true)
        }
    }
}
