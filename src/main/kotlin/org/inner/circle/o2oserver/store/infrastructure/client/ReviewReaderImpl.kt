package org.inner.circle.o2oserver.store.infrastructure.client

import org.inner.circle.o2oserver.store.domain.review.Review
import org.inner.circle.o2oserver.store.domain.review.ReviewQueryObject
import org.inner.circle.o2oserver.store.domain.review.ReviewReader
import org.springframework.stereotype.Component

@Component
class ReviewReaderImpl(private val reviewApiClient: ReviewApiClient) : ReviewReader {
    override fun getStoreReviewList(queryObject: ReviewQueryObject): List<Review> {
        val response = reviewApiClient.getStoreReviewList(queryObject.storeId, queryObject.page, queryObject.limit)
        return response.reviews.map { it.toDomain() }
    }
}
