package org.inner.circle.o2oserver.store.domain.review

import org.springframework.data.domain.Pageable

interface ReviewService {
    fun getStoreReviewList(storeId: Long, pageable: Pageable): List<ReviewInfo>
}
