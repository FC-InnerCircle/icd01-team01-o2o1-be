package org.inner.circle.o2oserver.member.infrastructure.client

import org.inner.circle.o2oserver.member.domain.Address
import org.inner.circle.o2oserver.member.domain.AddressReader
import org.inner.circle.o2oserver.member.infrastructure.repository.AddressRepository
import org.springframework.stereotype.Component

@Component
class AddressReaderImpl(
    private val addressRepository: AddressRepository,
) : AddressReader {
    override fun getAddresses(memberId: String): List<Address> {
        return addressRepository.findByMemberId(memberId)
    }
}
