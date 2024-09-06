package org.inner.circle.o2oserver.member.application

import org.inner.circle.o2oserver.commons.security.JsonWebToken
import org.inner.circle.o2oserver.commons.security.TokenProvider
import org.inner.circle.o2oserver.member.domain.LoginService
import org.inner.circle.o2oserver.member.domain.Member
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component

@Component
class LoginFacade(
    private val loginService: LoginService,
    private val tokenProvider: TokenProvider,
) {
    fun login(member: Member): Pair<JsonWebToken, Boolean> {
        val result = loginService.findOrCreateMember(member)
        val authentication =
            UsernamePasswordAuthenticationToken(result.member.id, null, emptyList())
        val jwtToken = tokenProvider.generateToken(authentication)

        return jwtToken to result.isSignup
    }
}
