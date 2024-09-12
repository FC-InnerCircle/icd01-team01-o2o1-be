package org.inner.circle.o2oserver.order.presentation.dto

class OrderReviewResponse {
    data class ReviewCreateResult(
        val reviewId: Long,
    ) {
        companion object {
            fun toResponse(reviewId: Long): ReviewCreateResult {
                return ReviewCreateResult(reviewId)
            }
        }
    }
}
