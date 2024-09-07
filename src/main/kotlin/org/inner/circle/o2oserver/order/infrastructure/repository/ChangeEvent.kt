package org.inner.circle.o2oserver.order.infrastructure.repository

data class ChangeEvent(
    val operationType: String,
    val documentKey: String,
    val fullDocument: String
)
