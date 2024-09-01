package org.inner.circle.o2oserver.member.presentation.dto

data class LoginResponse(
    val response: ResponseData,
    val statusCode: Int,
    val msg: String,
)

data class ResponseData(
    val isSignup: Boolean,
)
