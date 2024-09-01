package org.inner.circle.o2oserver.store.domain.review

data class ReviewQueryObject(
    val storeId: Int,
    val limit: Int,
    val page: Int,
)
