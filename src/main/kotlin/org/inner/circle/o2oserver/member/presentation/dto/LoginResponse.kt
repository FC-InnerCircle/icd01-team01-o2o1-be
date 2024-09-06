package org.inner.circle.o2oserver.member.presentation.dto

data class LoginResponseData(
    val isSignup: Boolean,
)

data class LoginResponse(
    val response: LoginResponseData,
    val statusCode: Int,
    val msg: String,
)
