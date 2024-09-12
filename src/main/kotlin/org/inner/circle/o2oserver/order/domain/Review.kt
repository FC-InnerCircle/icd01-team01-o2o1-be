package org.inner.circle.o2oserver.order.domain

import java.time.LocalDateTime

class Review(
    val reviewId: Long? = 0,
    val memberId: Long,
    val orderId: Long,
    val content: String,
    val rating: Int,
    val reviewImage: List<String>? = emptyList(),
    val timestamp: LocalDateTime = LocalDateTime.now(),
)
