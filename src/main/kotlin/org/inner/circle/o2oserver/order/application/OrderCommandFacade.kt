package org.inner.circle.o2oserver.order.application

import org.inner.circle.o2oserver.member.domain.MemberUseCase
import org.inner.circle.o2oserver.order.domain.OrderUseCase
import org.inner.circle.o2oserver.order.presentation.dto.OrderCancelResponse
import org.inner.circle.o2oserver.order.presentation.dto.OrderCreateRequest
import org.inner.circle.o2oserver.order.presentation.dto.OrderCreateResponse
import org.inner.circle.o2oserver.order.presentation.dto.OrderReviewRequest
import org.inner.circle.o2oserver.order.presentation.dto.OrderReviewResponse
import org.springframework.stereotype.Component

@Component
class OrderCommandFacade(
    private val orderUseCase: OrderUseCase,
    private val memberUseCase: MemberUseCase,
) {
    fun createOrder(orderCreate: OrderCreateRequest.OrderCreate, userName: String): OrderCreateResponse.OrderCreateResult {
        val member = memberUseCase.getMemberInfo(userName)
        val toOrder = OrderCreateRequest.OrderCreate.toOrder(orderCreate, member.memberId!!)
        val createOrder = orderUseCase.createOrder(toOrder)
        return OrderCreateResponse.OrderCreateResult.toResponse(createOrder)
    }

    fun cancelOrder(orderId: Long, username: String): OrderCancelResponse.OrderCancel {
        val member = memberUseCase.getMemberInfo(username)
        val cancelOrder = orderUseCase.cancelOrder(orderId, member.memberId!!)
        return OrderCancelResponse.OrderCancel.toResponse(cancelOrder.orderId!!)
    }

    fun createReview(
        orderId: Long,
        reviewCreate: OrderReviewRequest.ReviewCreate,
        username: String,
    ): OrderReviewResponse.ReviewCreateResult {
        val member = memberUseCase.getMemberInfo(username)
        val toReview = OrderReviewRequest.ReviewCreate.toDomain(reviewCreate, orderId, member.memberId!!)
        val createReview = orderUseCase.createReviewOrder(toReview)
        return OrderReviewResponse.ReviewCreateResult.toResponse(createReview.reviewId!!)
    }
}
