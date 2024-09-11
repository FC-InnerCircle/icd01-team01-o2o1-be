package org.inner.circle.o2oserver.commons.subscribe

import kotlinx.coroutines.flow.MutableSharedFlow
import org.springframework.stereotype.Component

@Component
class SubscriptionManager {
    private val subscriptions = mutableMapOf<Long, MutableSharedFlow<String>>()

    fun subscribe(memberId: Long): MutableSharedFlow<String> {
        return subscriptions.getOrPut(memberId) { MutableSharedFlow() }
    }

    fun unsubscribe(memberId: Long) {
        subscriptions.remove(memberId)
    }

    suspend fun emit(memberId: Long, event: String) {
        subscriptions[memberId]?.emit(event)
    }
}
