package org.inner.circle.o2oserver.store.domain.review

class ReviewInfo(
    review: Review,
) {
    val reviewId: Int? = review.reviewId
    val contents: String? = review.contents
    val reviewImages: List<String>? = review.reviewImages
    val rating: Int? = review.rating
    val reviewDate: String = review.reviewDate.toString()
}
