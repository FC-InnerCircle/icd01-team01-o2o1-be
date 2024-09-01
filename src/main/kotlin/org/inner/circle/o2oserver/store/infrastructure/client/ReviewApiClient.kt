package org.inner.circle.o2oserver.store.infrastructure.client

import org.inner.circle.o2oserver.store.infrastructure.dto.ReviewImageResponseDto
import org.inner.circle.o2oserver.store.infrastructure.dto.ReviewListResponseDto
import org.inner.circle.o2oserver.store.infrastructure.dto.ReviewResponseDto
import org.springframework.stereotype.Component

@Component
class ReviewApiClient {
    fun getStoreReviewList(storeId: Int, page: Int, size: Int): ReviewResponseDto {
        val reviews =
            listOf(
                ReviewListResponseDto(
                    reviewId = 1,
                    content = "리뷰 내용",
                    grade = 1,
                    images =
                        listOf(
                            ReviewImageResponseDto(1, "이미지경로"),
                            ReviewImageResponseDto(2, "이미지경로2"),
                        ),
                    reviewDate = "2024-08-20 20:00:00",
                ),
                ReviewListResponseDto(
                    reviewId = 2,
                    content = "리뷰 내용",
                    grade = 5,
                    images =
                        listOf(
                            ReviewImageResponseDto(1, "이미지경로"),
                            ReviewImageResponseDto(2, "이미지경로2"),
                        ),
                    reviewDate = "2024-08-20 20:00:00",
                ),
            )

        return ReviewResponseDto(
            grade = 3,
            reviews,
        )
    }
}
