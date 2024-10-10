package org.inner.circle.o2oserver.store.application

import org.inner.circle.o2oserver.store.domain.review.ReviewInfo
import org.inner.circle.o2oserver.store.domain.review.ReviewService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ReviewFacade(
    private val reviewService: ReviewService,
) {
    fun getStoreReviewList(storeId: Long, pageable: Pageable): List<ReviewInfo> {
        return reviewService.getStoreReviewList(storeId, pageable)
    }
}
