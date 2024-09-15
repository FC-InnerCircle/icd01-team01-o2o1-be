package org.inner.circle.o2oserver.member.domain

import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberReader: MemberReader,
    private val memberStore: MemberStore,
    private val addressStore: AddressStore,
    private val addressReader: AddressReader,
    private val sequenceGenerator: SequenceGenerator,
) : MemberUseCase {
    override fun getMemberInfo(id: String): Member {
        return memberReader.getMemberById(id)
    }

    override fun createMemberInfo(memberDetail: MemberDetail, address: Address) {
        val addressId = sequenceGenerator.generate("addressSequence")
        val addressInfo = createAddressInfo(addressId, address)

        addressStore.save(addressInfo)
        updateMemberInfo(memberDetail.id, memberDetail)
    }

    override fun updateMemberInfo(id: String, memberDetail: MemberDetail) {
        val existingMember = memberReader.getMemberById(id)
        val updatedMember = existingMember.updateMemberInfo(
            nickName = memberDetail.nickName,
            contact = memberDetail.contact,
        )
        memberStore.save(updatedMember)
    }

    override fun deleteMember(id: String) {
        memberStore.remove(id)
        addressStore.remove(id)
    }

    override fun createAddress(address: Address): Address {
        val addressId = sequenceGenerator.generate("addressSequence")
        val addressInfo = createAddressInfo(addressId, address)
        val newAddress = addressStore.save(addressInfo)

        return newAddress
    }

    override fun getAddresses(memberId: String): List<Address> {
        return addressReader.getAddresses(memberId)
    }

    override fun setDefaultAddress(memberId: String, addressId: Long) {
        val addresses = addressReader.getAddresses(memberId)
        addresses.forEach { address ->
            val updatedAddress = address.updateDefaultStatus(address.addressId == addressId)
            addressStore.save(updatedAddress)
        }
    }

    override fun deleteAddress(addressId: Long): Long {
        val removedAddressId = addressStore.removeAddress(addressId)

        return removedAddressId
    }

    private fun createAddressInfo(addressId: Long, address: Address): Address {
        return Address(
            addressId = addressId,
            memberId = address.memberId,
            address = address.address,
            addressDetail = address.addressDetail,
            longitude = address.longitude,
            latitude = address.latitude,
            zipCode = address.zipCode,
            isDefault = address.isDefault,
        )
    }
}
