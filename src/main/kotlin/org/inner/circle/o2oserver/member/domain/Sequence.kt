package org.inner.circle.o2oserver.member.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "sequences")
data class Sequence(
    @Id val id: String,
    val seq: Long,
)
