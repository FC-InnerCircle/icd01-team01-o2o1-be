package org.inner.circle.o2oserver.member.domain

interface AddressReader {
    fun getAddresses(memberId: String): List<Address>
}
