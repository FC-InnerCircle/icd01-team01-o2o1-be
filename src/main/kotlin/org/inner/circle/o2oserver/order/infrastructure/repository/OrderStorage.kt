package org.inner.circle.o2oserver.order.infrastructure.repository

import org.inner.circle.o2oserver.commons.exception.ErrorDetails
import org.inner.circle.o2oserver.commons.exception.Exceptions
import org.inner.circle.o2oserver.order.domain.Order
import org.inner.circle.o2oserver.order.domain.OrderReader
import org.inner.circle.o2oserver.order.domain.OrderStore
import org.inner.circle.o2oserver.order.domain.Review
import org.springframework.stereotype.Component

@Component
class OrderStorage(
    private val orderRepository: OrderRepository,
    private val reviewRepository: ReviewRepository,
) : OrderReader, OrderStore {
    override fun findOrderIsNullable(orderId: Long): Order? {
        val orderEntity = findByOrderIsNullable(orderId)
        return OrderEntity.toDomain(orderEntity)
    }

    override fun findOrder(orderId: Long): Order {
        val orderEntity = findByOrderId(orderId)
        return OrderEntity.toDomain(orderEntity)!!
    }

    override fun findOrdersByMember(memberId: Long): List<Order> {
        orderRepository.findByMemberId(memberId)?.let { orders ->
            return orders.map { orderEntity ->
                val reviewEntity = reviewRepository.findByOrderId(orderEntity.orderId)
                OrderEntity.toDomainByReview(orderEntity, reviewEntity)
            }
        } ?: throw Exceptions.BadRequestException(ErrorDetails.ORDER_NOT_FOUND.message)
    }

    override fun saveOrder(order: Order, orderId: Long): Order {
        val orderEntity = OrderEntity.toEntity(order, orderId)
        orderRepository.save(orderEntity)
        return order
    }

    override fun cancelOrder(orderId: Long): Long {
        val orderEntity = findByOrderId(orderId)
        val cancelOrderEntity = OrderEntity.cancelOrder(orderEntity)
        orderRepository.save(cancelOrderEntity)
        return orderId
    }

    override fun saveReview(review: Review): Review {
        val reviewEntity = ReviewEntity.toEntity(review)
        val savedReviewEntity = reviewRepository.save(reviewEntity)
        return ReviewEntity.toDomain(savedReviewEntity)
    }

    private fun findByOrderId(orderId: Long): OrderEntity {
        return orderRepository.findFirstByOrderByOrderId(orderId)
            ?: throw Exceptions.BadRequestException(ErrorDetails.ORDER_NOT_FOUND.message)
    }

    private fun findByOrderIsNullable(orderId: Long): OrderEntity? {
        return orderRepository.findFirstByOrderByOrderId(orderId)
    }
}
