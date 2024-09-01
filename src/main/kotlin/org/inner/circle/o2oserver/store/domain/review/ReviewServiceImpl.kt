package org.inner.circle.o2oserver.store.domain.review

import org.springframework.stereotype.Service

@Service
class ReviewServiceImpl(private val reviewReader: ReviewReader) : ReviewService {
    override fun getStoreReviewList(queryObject: ReviewQueryObject): List<ReviewInfo> {
        val reviews = reviewReader.getStoreReviewList(queryObject)
        return reviews.map { ReviewInfo(it) }
    }
}
