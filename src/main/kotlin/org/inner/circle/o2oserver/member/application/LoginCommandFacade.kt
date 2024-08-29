package org.inner.circle.o2oserver.member.application

import org.inner.circle.o2oserver.member.domain.LoginService
import org.springframework.stereotype.Component

@Component
class LoginCommandFacade(
    private val loginService: LoginService
) {
    fun login(accessToken: String): String {
        val info = loginService.login(accessToken)
        // 임시 로그인 성공 메시지
        return "login success"
    }
}
