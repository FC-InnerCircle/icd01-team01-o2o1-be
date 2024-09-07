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
        val existingMember = findExistingMember(member)

        return existingMember?.let {
            MemberCreationResult(it, false)
        } ?: run {
            val newMember = createNewMember(member)
            sendMemberDataToExternalServer(newMember)
            MemberCreationResult(newMember, true)
        }
    }

    private fun findExistingMember(member: Member): Member? {
        return memberReader.findBySnsTypeAndSubId(member.snsType, member.subId)
    }

    private fun createNewMember(member: Member): Member {
        val memberId = sequenceGenerator.generate("memberSequence")
        val memberInfo = Member(
            memberId = memberId,
            name = member.name,
            snsType = member.snsType,
            subId = member.subId,
        )
        return memberStore.save(memberInfo).also {
            log.info("New member created with ID: ${it.id}")
        }
    }

    private fun sendMemberDataToExternalServer(member: Member) {
        try {
            memberOutPort.sendMemberData(member)
        } catch (e: Exception) {
            throw RuntimeException(
                "External server communication failed, rolling back transaction",
                e,
            )
        }
    }
}
