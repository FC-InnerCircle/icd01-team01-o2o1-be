package org.inner.circle.o2oserver.order.infrastructure.repository

import org.inner.circle.o2oserver.commons.exception.ErrorDetails
import org.inner.circle.o2oserver.commons.exception.Exceptions
import org.inner.circle.o2oserver.order.domain.Order
import org.inner.circle.o2oserver.order.domain.OrderReader
import org.inner.circle.o2oserver.order.domain.OrderStore
import org.springframework.stereotype.Component

@Component
class OrderStorage(
    private val orderRepository: OrderRepository,
) : OrderReader, OrderStore {
    override fun findOrderDetailByOrderId(orderId: Long): Order {
        val orderEntity = orderRepository.findByOrderId(orderId)
            ?: throw Exceptions.BadRequestException(ErrorDetails.ORDER_NOT_FOUND.message)
        return OrderEntity.toDomain(orderEntity)
    }

    override fun findOrderListByMemberId(memberId: Long): List<Order> {
        orderRepository.findByMemberId(memberId)?.let {
            return it.map { OrderEntity.toDomain(it) }
        } ?: throw Exceptions.BadRequestException(ErrorDetails.ORDER_NOT_FOUND.message)
    }

    override fun saveOrder(order: Order): Order {
        val orderEntity = OrderEntity.toEntity(order)
        val savedOrderEntity = orderRepository.save(orderEntity)
        // find storeEntity
        return OrderEntity.toDomain(savedOrderEntity)
    }
}
