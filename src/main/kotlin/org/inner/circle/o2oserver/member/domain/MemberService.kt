package org.inner.circle.o2oserver.member.domain

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberReader: MemberReader,
    private val memberStore: MemberStore,
    private val addressStore: AddressStore,
    private val memberOutPort: MemberOutPort,
    private val sequenceGenerator: SequenceGenerator,
) : MemberUseCase {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun getMemberInfo(id: String): Member {
        return memberReader.getMemberById(id)
    }

    @Transactional
    override fun createMemberInfo(memberDetail: MemberDetail, address: Address) {
        val addressId = sequenceGenerator.generate("addressSequence")
        val addressInfo = createAddress(addressId, address)

        addressStore.save(addressInfo)
        updateMemberInfo(memberDetail.id, memberDetail)
        sendDataExternalServer(memberDetail, addressInfo)
    }

    @Transactional
    override fun updateMemberInfo(id: String, memberDetail: MemberDetail) {
        val existingMember = memberReader.getMemberById(id)
        val updatedMember = existingMember.copy(
            nickName = memberDetail.nickName,
            contact = memberDetail.contact,
        )
        memberStore.save(updatedMember)
    }

    private fun createAddress(addressId: Long, address: Address): Address {
        return Address(
            addressId = addressId,
            memberId = address.memberId,
            address = address.address,
            detail = address.detail,
            longitude = address.longitude,
            latitude = address.latitude,
            isDefault = address.isDefault,
        )
    }

    private fun sendDataExternalServer(memberDetail: MemberDetail, addressInfo: Address) {
        try {
            memberOutPort.sendMemberInfo(memberDetail, addressInfo)
        } catch (e: Exception) {
            log.error("External server communication failed, rolling back transaction", e)
            throw RuntimeException("External server communication failed, rolling back transaction", e)
        }
    }
}
