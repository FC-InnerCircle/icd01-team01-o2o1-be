package org.inner.circle.o2oserver.store.infrastructure.dto

import org.inner.circle.o2oserver.store.domain.review.Review
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class ReviewImageResponseDto(
    val seq: Int,
    val imageUrl: String
)

data class ReviewListResponseDto(
    val reviewId: Int,
    val content: String,
    val grade: Int,
    val images: List<ReviewImageResponseDto>,
    val reviewDate: String
) {
    fun toDomain(): Review {
        return Review(
            reviewId = reviewId,
            reviewImages = images.map { it.imageUrl },
            reviewDate = LocalDateTime.parse(reviewDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            rating = grade,
            contents = content
        )
    }
}

data class ReviewResponseDto(
    val grade: Int,
    val reviews: List<ReviewListResponseDto>
)
