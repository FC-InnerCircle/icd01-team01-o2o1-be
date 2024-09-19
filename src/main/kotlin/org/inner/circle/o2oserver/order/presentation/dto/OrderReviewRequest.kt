package org.inner.circle.o2oserver.order.presentation.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.inner.circle.o2oserver.order.domain.Review

class OrderReviewRequest {
    data class ReviewCreate(
        @field:NotBlank(message = "리뷰 내용은 필수입니다.")
        val content: String,

        @field:Min(value = 0, message = "평점은 0 이상이어야 합니다.")
        @field:Max(value = 5, message = "평점은 5 이하이어야 합니다.")
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
