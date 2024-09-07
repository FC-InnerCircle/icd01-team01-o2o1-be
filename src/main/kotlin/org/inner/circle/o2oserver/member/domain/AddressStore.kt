package org.inner.circle.o2oserver.member.domain

interface AddressStore {
    fun save(address: Address): Address

    fun remove(memberId: String)

    fun removeAddress(addressId: Long): Long
}
