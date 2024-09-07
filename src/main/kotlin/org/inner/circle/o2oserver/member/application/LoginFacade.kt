package org.inner.circle.o2oserver.member.application

import org.inner.circle.o2oserver.commons.security.JsonWebToken
import org.inner.circle.o2oserver.commons.security.TokenProvider
import org.inner.circle.o2oserver.member.domain.ExternalService
import org.inner.circle.o2oserver.member.domain.LoginService
import org.inner.circle.o2oserver.member.domain.Member
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class LoginFacade(
    private val loginService: LoginService,
    private val externalService: ExternalService,
    private val tokenProvider: TokenProvider,
) {
    @Transactional
    fun login(member: Member): Pair<JsonWebToken, Boolean> {
        val result = loginService.findOrCreateMember(member)

        if (result.isSignup) {
            externalService.sendMemberData(result.member)
        }

        val authentication =
            UsernamePasswordAuthenticationToken(result.member.id, null, emptyList())
        val jwtToken = tokenProvider.generateToken(authentication)

        return jwtToken to result.isSignup
    }
}
