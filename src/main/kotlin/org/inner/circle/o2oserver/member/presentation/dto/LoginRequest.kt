package org.inner.circle.o2oserver.member.presentation.dto

data class LoginRequest(
    val snsType: String,
    val name: String,
    val subId: String,
    val email: String,
)
