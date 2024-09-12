package org.inner.circle.o2oserver.order.presentation.dto

import org.inner.circle.o2oserver.order.domain.Review

class OrderReviewRequest {
    data class ReviewCreate(
        val content: String,
        val rating: Double,
        val reviewImage: List<String>?,
    ) {
        companion object {
            fun toDomain(reviewCreate: ReviewCreate, orderId: Long, memberId: Long): Review {
                return Review(
                    memberId = memberId,
                    orderId = orderId,
                    content = reviewCreate.content,
                    rating = reviewCreate.rating,
                    reviewImage = reviewCreate.reviewImage,
                )
            }
        }
    }
}
