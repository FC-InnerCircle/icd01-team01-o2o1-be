package org.inner.circle.o2oserver.member.infrastructure.repository

import org.inner.circle.o2oserver.member.domain.Address
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AddressRepository : MongoRepository<Address, String> {
}
