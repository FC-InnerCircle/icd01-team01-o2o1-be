package org.inner.circle.o2oserver.store.domain.review

interface ReviewService {
    fun getStoreReviewList(queryObject: ReviewQueryObject): List<ReviewInfo>
}
