package org.inner.circle.o2oserver.store.domain.review

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ReviewServiceImpl(private val reviewReader: ReviewReader) : ReviewService {
    override fun getStoreReviewList(storeId: Long, pageable: Pageable): List<ReviewInfo> {
        val reviews = reviewReader.getStoreReviewList(storeId, pageable)
        return reviews.map { ReviewInfo(it) }
    }
}
