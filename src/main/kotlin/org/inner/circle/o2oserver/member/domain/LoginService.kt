package org.inner.circle.o2oserver.member.domain

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class LoginService(
    private val memberReader: MemberReader,
    private val memberStore: MemberStore,
    private val sequenceGenerator: SequenceGenerator,
) : LoginUseCase {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun findOrCreateMember(member: Member): MemberCreationResult {
        val existingMember = findExistingMember(member)
        val isNewMember = !existingMember?.nickName.isNullOrEmpty()

        return existingMember?.let {
            MemberCreationResult(it, isNewMember)
        } ?: run {
            val newMember = createNewMember(member)
            MemberCreationResult(newMember, false)
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
}
