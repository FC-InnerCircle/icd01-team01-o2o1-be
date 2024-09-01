package org.inner.circle.o2oserver.store.domain.review

interface ReviewReader {
    fun getStoreReviewList(queryObject: ReviewQueryObject): List<Review>
}
