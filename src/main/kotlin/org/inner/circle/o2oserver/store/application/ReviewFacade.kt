package org.inner.circle.o2oserver.store.application

import org.inner.circle.o2oserver.store.domain.review.ReviewQueryObject
import org.inner.circle.o2oserver.store.domain.review.ReviewService
import org.springframework.stereotype.Service

@Service
class ReviewFacade(
    private val reviewService: ReviewService,
    private val storeService: ReviewService,
) {
    fun getStoreReviewList(queryObject: ReviewQueryObject) {
        val reviewList = reviewService.getStoreReviewList(queryObject)
    }
}
