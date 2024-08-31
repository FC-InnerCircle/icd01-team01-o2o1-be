package org.inner.circle.o2oserver.commons.models

data class BaseResponse(
    val response: Any,
    val statusCode: Int,
    val msg: String
)
