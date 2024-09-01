package org.inner.circle.o2oserver.member.application

import org.inner.circle.o2oserver.commons.security.JsonWebToken
import org.inner.circle.o2oserver.commons.security.TokenProvider
import org.inner.circle.o2oserver.member.domain.LoginService
import org.inner.circle.o2oserver.member.domain.Member
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component

@Component
class LoginFacade(
    private val loginService: LoginService, private val tokenProvider: TokenProvider
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun login(member: Member): Pair<JsonWebToken, Boolean> {
        val result = loginService.findOrCreateMember(member)
        val authentication =
            UsernamePasswordAuthenticationToken(result.member.memberId, null, emptyList())
        val jwtToken = tokenProvider.generateToken(authentication)

        return jwtToken to result.isSignup
    }
}
