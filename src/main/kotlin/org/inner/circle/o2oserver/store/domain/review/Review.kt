package org.inner.circle.o2oserver.store.domain.review

import java.time.LocalDateTime

class Review(
    val reviewId: Int?,
    val contents: String?,
    val rating: Int?,
    val reviewImages: List<String>?,
    val reviewDate: LocalDateTime
)
