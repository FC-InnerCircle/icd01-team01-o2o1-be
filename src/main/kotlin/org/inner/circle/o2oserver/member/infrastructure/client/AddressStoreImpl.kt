package org.inner.circle.o2oserver.member.infrastructure.client

import org.inner.circle.o2oserver.member.domain.Address
import org.inner.circle.o2oserver.member.domain.AddressStore
import org.inner.circle.o2oserver.member.infrastructure.repository.AddressRepository
import org.springframework.stereotype.Component

@Component
class AddressStoreImpl(
    private val addressRepository: AddressRepository,
) : AddressStore {
    override fun save(address: Address): Address {
        return addressRepository.save(address)
    }

    override fun remove(memberId: String) {
        addressRepository.deleteByMemberId(memberId)
    }

    override fun removeAddress(addressId: Long): Long {
        addressRepository.deleteById(addressId)
        return addressId
    }
}
