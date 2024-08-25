package org.inner.circle.o2oserver.commons.security

data class JsonWebToken(
    val accessToken: String,
    val refreshToken: String
)
