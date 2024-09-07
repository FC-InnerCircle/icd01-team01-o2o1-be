package org.inner.circle.o2oserver.member.domain

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberReader: MemberReader,
    private val memberStore: MemberStore,
    private val addressStore: AddressStore,
    private val addressReader: AddressReader,
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
        val addressInfo = createAddressInfo(addressId, address)

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

    @Transactional
    override fun deleteMember(id: String) {
        val existingMember = memberReader.getMemberById(id)
        memberStore.remove(id)
        addressStore.remove(id)
        sendDeleteRequestToExternalServer(existingMember.memberId!!)
    }

    @Transactional
    override fun createAddress(address: Address): Address {
        val addressId = sequenceGenerator.generate("addressSequence")
        val addressInfo = createAddressInfo(addressId, address)
        val newAddress = addressStore.save(addressInfo)
        sendCreateAddressRequestToExternalServer(addressInfo)

        return newAddress
    }

    override fun getAddresses(memberId: String): List<Address> {
        return addressReader.getAddresses(memberId)
    }

    @Transactional
    override fun setDefaultAddress(memberId: String, addressId: Long) {
        val addresses = addressReader.getAddresses(memberId)
        addresses.forEach {
            val updatedAddress = if (it.addressId == addressId) {
                it.copy(isDefault = true)
            } else {
                it.copy(isDefault = false)
            }
            addressStore.save(updatedAddress)
        }
    }

    @Transactional
    override fun deleteAddress(addressId: Long): Long {
        val removedAddressId = addressStore.removeAddress(addressId)
        sendDeleteAddressRequestToExternalServer(removedAddressId)

        return removedAddressId
    }

    private fun createAddressInfo(addressId: Long, address: Address): Address {
        return Address(
            addressId = addressId,
            memberId = address.memberId,
            address = address.address,
            detail = address.detail,
            longitude = address.longitude,
            latitude = address.latitude,
            zipCode = address.zipCode,
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

    private fun sendDeleteRequestToExternalServer(memberId: Long) {
        try {
            memberOutPort.sendDeleteMemberRequest(memberId)
        } catch (e: Exception) {
            log.error("Failed to send delete request to external server, rolling back transaction", e)
            throw RuntimeException("Failed to send delete request, rolling back transaction", e)
        }
    }

    private fun sendCreateAddressRequestToExternalServer(addressInfo: Address) {
        try {
            memberOutPort.sendCreateAddressRequest(addressInfo)
        } catch (e: Exception) {
            log.error("Failed to send create address request to external server, rolling back transaction", e)
            throw RuntimeException("Failed to send create address request, rolling back transaction", e)
        }
    }

    private fun sendDeleteAddressRequestToExternalServer(addressId: Long) {
        try {
            memberOutPort.sendDeleteAddressRequest(addressId)
        } catch (e: Exception) {
            log.error("Failed to send delete address request to external server, rolling back transaction", e)
            throw RuntimeException("Failed to send delete address request, rolling back transaction", e)
        }
    }
}
