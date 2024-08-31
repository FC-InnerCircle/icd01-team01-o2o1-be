package org.inner.circle.o2oserver.commons.security

import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.inner.circle.o2oserver.commons.exception.CustomAuthenticationException
import org.inner.circle.o2oserver.commons.security.AuthFilter.Companion.REFRESH_TOKEN
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class AuthFilter(
    private val jwtTokenProvider: TokenProvider
) : OncePerRequestFilter() {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 헤더에 들어갈 키값
     * - Authorization: 액세스 토큰
     * - Refresh-Token: 리프레시 토큰
     * 해당 키이름을 사용해서 로그인 성공 시 반환해야함
     */
    companion object {
        const val AUTHORIZATION = "Authorization"
        const val REFRESH_TOKEN = "RefreshToken"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val accessToken = jwtTokenProvider.resolveToken(request, AUTHORIZATION)
            if (jwtTokenProvider.validateToken(accessToken!!)) {
                val authentication = jwtTokenProvider.getAuthentication(accessToken)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: ExpiredJwtException) {
            log.debug("액세스 토큰이 만료되어 재발급을 진행합니다.")
            handleExpiredToken(request, response)
        } catch (e: Exception) {
            log.error("토큰 검증 실패")
        }

        filterChain.doFilter(request, response)
    }

    private fun handleExpiredToken(
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        try {
            val refreshToken = jwtTokenProvider.resolveToken(request, REFRESH_TOKEN)
            if (jwtTokenProvider.validateToken(refreshToken!!)) {
                val mail = jwtTokenProvider.findByRefreshToken(refreshToken)
                val authentication = jwtTokenProvider.getAuthentication(refreshToken)
                if (mail != authentication.name) {
                    throw CustomAuthenticationException("토큰 검증 실패")
                }
                val newAccessToken = jwtTokenProvider.generateToken(authentication)
                response.setHeader(AUTHORIZATION, "Bearer $newAccessToken")
                response.setHeader(REFRESH_TOKEN, refreshToken)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: Exception) {
            log.error("토큰 재발급 실패. 다시 로그인해 주세요.")
        }
    }
}
