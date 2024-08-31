package org.inner.circle.o2oserver.member.application

import org.inner.circle.o2oserver.member.domain.LoginService
import org.springframework.stereotype.Component

@Component
class LoginFacade(
    private val loginService: LoginService
) {
    fun login(accessToken: String): String {
        val info = loginService.login(accessToken)
        return "login success"
    }
}
