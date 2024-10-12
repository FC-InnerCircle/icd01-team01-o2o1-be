package org.inner.circle.o2oserver.store.infrastructure.client

import org.inner.circle.o2oserver.store.domain.review.Review
import org.inner.circle.o2oserver.store.domain.review.ReviewReader
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class ReviewReaderImpl(private val reviewApiClient: ReviewApiClient) : ReviewReader {
    override fun getStoreReviewList(storeId: Long, pageable: Pageable): List<Review> {
        val response = reviewApiClient.getStoreReviewList(storeId, pageable.pageNumber, pageable.pageSize)
        return response.reviews.map { it.toDomain() }
    }
}
