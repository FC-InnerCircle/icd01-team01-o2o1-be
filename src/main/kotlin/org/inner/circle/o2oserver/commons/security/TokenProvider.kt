package org.inner.circle.o2oserver.commons.security

import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import jakarta.servlet.http.HttpServletRequest
import org.inner.circle.o2oserver.commons.exception.Exceptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Collectors

@Component
class TokenProvider(
) {
    @Value("\${jwt.secret}")
    private lateinit var seceretKey: String
    private val log = KotlinLogging.logger {}
    private val accessToken = "AccessToken"

//    private val accessExpiresIn: Long = 3600
    private val accessExpiresIn: Long = 604800
    private val refreshExpiresIn: Long = 604800
    private val refreshToken = "refreshToken"
    private val keyRole = "role"

    fun generateToken(authentication: Authentication): JsonWebToken {
        val newAccessToken = generateAccessToken(authentication)
        val newRefreshToken = generateRefreshToken(authentication, newAccessToken)
        return JsonWebToken(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )
    }

    fun generateAccessToken(authentication: Authentication): String {
        return createToken(authentication, accessExpiresIn) // 1 hour
    }

    fun generateRefreshToken(
        authentication: Authentication,
        accessToken: String
    ): String {
        val refreshToken = createToken(authentication, refreshExpiresIn) // 1 week
        return refreshToken.also {
            findAndSaveRefreshToken(authentication, accessToken, refreshToken)
        }
    }

    fun findAndSaveRefreshToken(
        authentication: Authentication,
        accessToken: String,
        refreshToken: String
    ) {
        val now = LocalDateTime.now()
        val accessExpire = Date.from(now.plusSeconds(accessExpiresIn).atZone(java.time.ZoneId.systemDefault()).toInstant())
        val refreshExpire = Date.from(now.plusSeconds(refreshExpiresIn).atZone(java.time.ZoneId.systemDefault()).toInstant())
//        authentication.name?.let {
//            redisStorage.findByToken(refreshToken)?.let { email ->
//                redisStorage.refreshTokenSave(refreshToken, email)
//            }
//            val authInfo: Auth =
//                authRepository.findByMemberEmail(it)?.let { tokenAuth ->
//                    tokenAuth.accessToken = accessToken
//                    tokenAuth.accessTokenExpiresIn = accessExpire.time
//                    tokenAuth.refreshToken = refreshToken
//                    tokenAuth.refreshTokenExpiresIn = refreshExpire.time
//                    tokenAuth
//                } ?: Auth(
//                    memberEmail = it,
//                    providerId = "local",
//                    connectTime = now.toString(),
//                    profileImageUrl = "",
//                    oauthToken = "",
//                    registration = "local",
//                    accessToken = accessToken,
//                    accessTokenExpiresIn = accessExpire.time,
//                    refreshToken = refreshToken,
//                    refreshTokenExpiresIn = refreshExpire.time
//                )
//            redisStorage.refreshTokenSave(authInfo.refreshToken!!, authInfo.memberEmail)
//            authRepository.save(authInfo)
//        }
    }

    fun createToken(
        authentication: Authentication,
        expire: Long
    ): String {
        val now = LocalDateTime.now()
        val authorities =
            authentication.authorities
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining())

        return Jwts.builder()
            .setSubject(authentication.name)
            .claim(keyRole, authorities)
            .setIssuedAt(Date.from(now.atZone(java.time.ZoneId.systemDefault()).toInstant()))
            .setExpiration(Date.from(now.plusSeconds(expire).atZone(java.time.ZoneId.systemDefault()).toInstant()))
            .signWith(SignatureAlgorithm.HS256, seceretKey.toByteArray())
            .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val claims: Claims =
            Jwts.parser()
                .setSigningKey(seceretKey.toByteArray())
                .parseClaimsJws(token)
                .body

        val authorities = listOf(SimpleGrantedAuthority("ROLE_USER"))
        val principal = User(claims.subject, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, null, authorities)
    }

    fun getAuthEmail(auth: String?): String? {
        try {
            return auth?.let {
                val token = auth.removePrefix("Bearer ")
                val authentication = getAuthentication(token)
                authentication.name
            }
        } catch (e: Exception) {
            log.error { "토큰 검증 실패 : ${e.message}" }
            return null
        }
    }

//    fun getAuthorities(claims: Claims): List<SimpleGrantedAuthority> {
//        return claims[keyRole]?.let {
//            listOf(SimpleGrantedAuthority(it.toString()))
//        } ?: throw Exceptions.UnauthorizedException("권한이 없습니다.")
//    }
//
//    fun againRefreshToken(token: String) {
//        val auth =
//            authRepository.findByRefreshToken(token) ?: {
//                Exceptions.UnauthorizedException("리프레시 토큰이 만료되었습니다.")
//            }
//    }

    fun findByRefreshToken(token: String): String {
//        return redisStorage.findByToken(token) ?: throw Exceptions.UnauthorizedException("리프레시 토큰이 만료되었습니다.")
        return ""
    }

    fun validateToken(token: String): Boolean {
        return try {
            val parseClaimsJws = Jwts.parser().setSigningKey(seceretKey.toByteArray()).parseClaimsJws(token)
            log.info { "Token validate :  ${parseClaimsJws.body}" }
            true
        } catch (e: SecurityException) {
            log.info { "잘못된 JWT 서명입니다." }
            throw e
        } catch (e: MalformedJwtException) {
            log.info { "잘못된 JWT 서명입니다." }
            throw e
        } catch (e: ExpiredJwtException) {
            log.info { "만료된 JWT 토큰입니다." }
            throw e
        } catch (e: UnsupportedJwtException) {
            log.info { "지원되지 않는 JWT 토큰입니다." }
            throw e
        } catch (e: IllegalArgumentException) {
            log.info { "JWT 토큰이 잘못되었습니다." }
            throw e
        } catch (e: NullPointerException) {
            log.info { "JWT 토큰이 없습니다." }
            throw e
        } catch (e: Exception) {
            log.error { "토큰 검증 실패 : ${e.message}" }
            throw e
        }
    }

    fun resolveToken(
        request: HttpServletRequest,
        header: String
    ): String? {
        if (header == refreshToken) {
            val cookies = request.cookies ?: throw Exceptions.UnauthorizedException("검증 토큰이 없습니다. 재로그인해주세요.")
            return cookies.find { it.name == refreshToken }?.value
        }

        val bearerToken: String = request.getHeader(header)
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else {
            null
        }
    }
}
