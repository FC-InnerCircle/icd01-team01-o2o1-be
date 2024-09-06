package org.inner.circle.o2oserver.member.infrastructure.client

import org.inner.circle.o2oserver.member.domain.Sequence
import org.inner.circle.o2oserver.member.domain.SequenceGenerator
import org.inner.circle.o2oserver.member.infrastructure.repository.SequenceRepository
import org.springframework.stereotype.Component

@Component
class MongoSequenceGenerator(
    private val sequenceRepository: SequenceRepository,
) : SequenceGenerator {
    override fun generate(sequenceName: String): Long {
        val sequence = sequenceRepository.findById(sequenceName).orElse(
            Sequence(id = sequenceName, seq = 0),
        )
        val newSeq = sequence.seq + 1

        sequenceRepository.save(sequence.copy(seq = newSeq)) // 시퀀스 값 업데이트
        return newSeq
    }
}
