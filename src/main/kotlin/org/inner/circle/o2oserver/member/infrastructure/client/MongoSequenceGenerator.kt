package org.inner.circle.o2oserver.member.infrastructure.client

import org.inner.circle.o2oserver.member.domain.Sequence
import org.inner.circle.o2oserver.member.domain.SequenceGenerator
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Component

@Component
class MongoSequenceGenerator(
    private val mongoTemplate: MongoTemplate,
) : SequenceGenerator {
    override fun generate(sequenceName: String): Long {
        val query = Query(Criteria.where("_id").`is`(sequenceName))
        val update = Update().inc("seq", 1)
        val options = FindAndModifyOptions().returnNew(true).upsert(true)

        val updatedSequence = mongoTemplate.findAndModify(query, update, options, Sequence::class.java)
            ?: throw RuntimeException("Failed to increment sequence for: $sequenceName")

        return updatedSequence.seq
    }
}
