package org.inner.circle.o2oserver.commons.security


import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.inner.circle.o2oserver.commons.exception.CustomAuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class AuthFilter(
    private val jwtTokenProvider: TokenProvider
) : OncePerRequestFilter() {
    private val log = KotlinLogging.logger {}
    private val authorization = "Authorization"
    private val refreshToken = "refreshToken"

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val accessToken = jwtTokenProvider.resolveToken(request, authorization)
            if (jwtTokenProvider.validateToken(accessToken!!)) {
                val authentication = jwtTokenProvider.getAuthentication(accessToken)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: ExpiredJwtException) {
            log.debug { "액세스 토큰이 만료되어 재발급을 진행합니다. " }
            handleExpiredToken(request, response)
        } catch (e: Exception) {
            val responseMessage = "토큰 검증 실패"
            log.error { responseMessage }
        }

        filterChain.doFilter(request, response)
    }

    private fun setAuthentication(token: String) {
        val authentication = jwtTokenProvider.getAuthentication(token)
        SecurityContextHolder.getContext().authentication = authentication
    }

    private fun handleExpiredToken(
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        try {
            val refreshToken = jwtTokenProvider.resolveToken(request, refreshToken)
            if (jwtTokenProvider.validateToken(refreshToken!!)) {
                val mail = jwtTokenProvider.findByRefreshToken(refreshToken)
                val authentication = jwtTokenProvider.getAuthentication(refreshToken)
                if (mail != authentication.name) {
                    throw CustomAuthenticationException("토큰 검증 실패")
                }
                val newAccessToken = jwtTokenProvider.generateAccessToken(authentication)
                response.setHeader(authorization, "Bearer $newAccessToken")
                setAuthentication(refreshToken)
            }
        } catch (e: Exception) {
            val responseMessage = "토큰 재발급 실패. 다시 로그인해 주세요."
            log.error { responseMessage }
        }
    }
}
