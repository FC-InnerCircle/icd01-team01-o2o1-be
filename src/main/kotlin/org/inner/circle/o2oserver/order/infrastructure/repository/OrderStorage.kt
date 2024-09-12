package org.inner.circle.o2oserver.order.infrastructure.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.inner.circle.o2oserver.commons.exception.ErrorDetails
import org.inner.circle.o2oserver.commons.exception.Exceptions
import org.inner.circle.o2oserver.order.domain.Delivery
import org.inner.circle.o2oserver.order.domain.Order
import org.inner.circle.o2oserver.order.domain.OrderReader
import org.inner.circle.o2oserver.order.domain.OrderStore
import org.inner.circle.o2oserver.order.domain.Review
import org.springframework.stereotype.Component

@Component
class OrderStorage(
    private val orderRepository: OrderRepository,
    private val reviewRepository: ReviewRepository,
    private val orderListener: OrderListener,
) : OrderReader, OrderStore {
    override fun findOrderDetailByOrderId(orderId: Long): Order {
        val orderEntity = findByOrderId(orderId)
        return OrderEntity.toDomain(orderEntity)
    }

    override fun findOrderListByMemberId(memberId: Long): List<Order> {
        orderRepository.findByMemberId(memberId)?.let { orders ->
            return orders.map { orderEntity ->
                val reviewEntity = reviewRepository.findByOrderId(orderEntity.orderId)
                OrderEntity.toDomainByReview(orderEntity, reviewEntity)
            }
        } ?: throw Exceptions.BadRequestException(ErrorDetails.ORDER_NOT_FOUND.message)
    }

    override fun subscribeDelivery(orderId: Long, memberId: Long): Flow<Delivery> =
        flow {
            val findByOrderId = findByOrderId(orderId)
            if (findByOrderId.memberId != memberId) {
                throw Exceptions.BadRequestException(ErrorDetails.ORDER_NOT_FOUND.message)
            }

            orderListener.subscribeDelivery(orderId)
                .map { emit(DeliveryEntity.toDomain(it)) }
        }

    override fun subscribeOrder(orderId: Long, memberId: Long): Flow<Order> =
        flow {
            orderListener.subscribeOrderStatus(orderId)
                .map { emit(OrderStatusEntity.toDomain(it)) }
        }

    override fun saveOrder(order: Order): Order {
        val orderEntity = OrderEntity.toEntity(order)
        val savedOrderEntity = orderRepository.save(orderEntity)
        return OrderEntity.toDomain(savedOrderEntity)
    }

    override fun cancelOrder(orderId: Long): Order {
        val orderEntity = findByOrderId(orderId)
        val cancelOrderEntity = OrderEntity.cancelOrder(orderEntity)
        val saveEntity = orderRepository.save(cancelOrderEntity)
        return OrderEntity.toDomain(saveEntity)
    }

    override fun saveReview(review: Review): Review {
        val reviewEntity = ReviewEntity.toEntity(review)
        val savedReviewEntity = reviewRepository.save(reviewEntity)
        return ReviewEntity.toDomain(savedReviewEntity)
    }

    private fun findByOrderId(orderId: Long): OrderEntity {
        return orderRepository.findByOrderId(orderId)
            ?: throw Exceptions.BadRequestException(ErrorDetails.ORDER_NOT_FOUND.message)
    }
}
