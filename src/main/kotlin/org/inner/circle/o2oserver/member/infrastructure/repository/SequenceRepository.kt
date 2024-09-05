package org.inner.circle.o2oserver.member.infrastructure.repository

import org.inner.circle.o2oserver.member.domain.Sequence
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface SequenceRepository : MongoRepository<Sequence, String>
