package org.inner.circle.o2oserver.order.infrastructure.repository

import org.bson.types.ObjectId
import org.inner.circle.o2oserver.order.domain.Review
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.time.ZoneId

@Document("review")
class ReviewEntity(
    @Id
    val id: ObjectId = ObjectId.get(),
    val reviewId: Long? = 0,
    val memberId: Long,
    val orderId: Long,
    val content: String,
    val rating: Double,
    val reviewImage: List<String>? = emptyList(),
    val createdAt: LocalDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul")),
) {
    companion object {
        fun toEntity(review: Review): ReviewEntity {
            return ReviewEntity(
                reviewId = review.reviewId,
                memberId = review.memberId,
                orderId = review.orderId,
                content = review.content,
                rating = review.rating,
                reviewImage = review.reviewImage,
            )
        }

        fun toDomain(savedReviewEntity: ReviewEntity): Review {
            return Review(
                reviewId = savedReviewEntity.reviewId,
                memberId = savedReviewEntity.memberId,
                orderId = savedReviewEntity.orderId,
                content = savedReviewEntity.content,
                rating = savedReviewEntity.rating,
                reviewImage = savedReviewEntity.reviewImage,
                timestamp = savedReviewEntity.createdAt,
            )
        }
    }
}
