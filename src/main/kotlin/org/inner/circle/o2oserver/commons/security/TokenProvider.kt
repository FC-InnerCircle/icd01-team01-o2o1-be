package org.inner.circle.o2oserver.commons.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.Jwts.SIG.HS256
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import jakarta.servlet.http.HttpServletRequest
import org.inner.circle.o2oserver.commons.exception.Exceptions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date

@Component
class TokenProvider(
    private val redisTemplate: RedisTemplate<String, String>
) {
    @Value("\${jwt.key}")
    private lateinit var secretKey: String

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
        const val ACCESS_EXPIRES: Long = 30 * 60 * 1000 // 30분을 밀리초로
        const val REFRESH_EXPIRES: Long = 7 * 24 * 60 * 60 * 1000 // 1주일을 밀리초로
    }

    /**
     * 로그인 시 토큰 생성하는 함수 액세스토큰, 리프레쉬토큰 함께 반환
     */
    fun generateToken(authentication: Authentication): JsonWebToken {
        val newAccessToken = generateToken(authentication, ACCESS_EXPIRES)
        val newRefreshToken = generateRefreshToken(authentication, newAccessToken, REFRESH_EXPIRES)
        return JsonWebToken(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )
    }

    fun generateToken(
        authentication: Authentication,
        expiresIn: Long
    ): String {
        val key = HS256.key().build()
        return Jwts.builder()
            .header()
            .keyId(secretKey)
            .and()
            .expiration(Date(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli() + expiresIn))
            .subject(authentication.name)
            .signWith(key)
            .compact()
    }

    fun generateRefreshToken(
        authentication: Authentication,
        accessToken: String,
        expiresIn: Long
    ): String {
        val refreshToken = generateToken(authentication, REFRESH_EXPIRES) // 1 week
        return refreshToken.also {
            saveRedisByRefreshToken(authentication, accessToken, refreshToken)
        }
    }

    fun saveRedisByRefreshToken(
        authentication: Authentication,
        accessToken: String,
        refreshToken: String
    ) {
        authentication.name?.let { authName ->
            redisTemplate.opsForValue().get(refreshToken)?.let { name ->
                redisTemplate.opsForValue().set(refreshToken, name)
            } ?: redisTemplate.opsForValue().set(refreshToken, authName)
        }
    }

    fun getAuthentication(token: String): Authentication {
        val key = HS256.key().build()
        val claims: Jws<Claims>? =
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)

        claims?.payload?.let {
            val authorities = listOf(SimpleGrantedAuthority("ROLE_USER"))
            val principal = User(it.subject, "", authorities)
            return UsernamePasswordAuthenticationToken(principal, null, authorities)
        } ?: throw Exceptions.UnValidTokenException("토큰이 유효하지 않습니다.")
    }

    fun findByRefreshToken(token: String): String? {
        return redisTemplate.opsForValue().get(token)
    }

    fun validateToken(token: String): Boolean {
        return try {
            val auth = getAuthentication(token)
            log.info("Token validate :  ${auth.principal}")
            auth.isAuthenticated
        } catch (e: SecurityException) {
            log.info("잘못된 JWT 서명입니다.")
            throw e
        } catch (e: MalformedJwtException) {
            log.info("잘못된 JWT 서명입니다.")
            throw e
        } catch (e: ExpiredJwtException) {
            log.info("만료된 JWT 토큰입니다.")
            throw e
        } catch (e: UnsupportedJwtException) {
            log.info("지원되지 않는 JWT 토큰입니다.")
            throw e
        } catch (e: IllegalArgumentException) {
            log.info("JWT 토큰이 잘못되었습니다.")
            throw e
        } catch (e: NullPointerException) {
            log.info("JWT 토큰이 없습니다.")
            throw e
        } catch (e: Exception) {
            log.error("토큰 검증 실패 : ${e.message}")
            throw e
        }
    }

    fun resolveToken(
        request: HttpServletRequest,
        header: String
    ): String? {
        val bearerToken: String = request.getHeader(header)
        return if (header == "refreshToken") {
            bearerToken
        } else if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else {
            null
        }
    }
}
