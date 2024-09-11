package org.inner.circle.o2oserver.commons.enums

import org.inner.circle.o2oserver.commons.exception.Exceptions.UnCancellableStatusException

enum class OrderStatus {
    PENDING,
    ACCEPTED,
    PREPARING,
    DELIVERING,
    DELIVERED,
    CANCELED,
    ;

    fun isStatusByThrowException(): Boolean =
        when (this) {
            PENDING, ACCEPTED -> true
            PREPARING -> throw UnCancellableStatusException("조리 중인 주문입니다.")
            DELIVERING -> throw UnCancellableStatusException("배송 중인 주문입니다.")
            DELIVERED -> throw UnCancellableStatusException("이미 배송된 주문입니다.")
            CANCELED -> throw UnCancellableStatusException("이미 취소된 주문입니다.")
        }

    fun isStatus(): Boolean =
        when (this) {
            PENDING, ACCEPTED -> true
            PREPARING, DELIVERING, DELIVERED, CANCELED -> false
        }
}
