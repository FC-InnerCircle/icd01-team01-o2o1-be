package org.inner.circle.o2oserver.store.domain.review

import org.springframework.data.domain.Pageable

interface ReviewReader {
    fun getStoreReviewList(storeId: Long, pageable: Pageable): List<Review>
}
