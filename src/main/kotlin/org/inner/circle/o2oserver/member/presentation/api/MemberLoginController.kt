package org.inner.circle.o2oserver.member.presentation.api

import org.inner.circle.o2oserver.member.application.LoginService
import org.inner.circle.o2oserver.member.presentation.dto.LoginRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberLoginController(
    private val loginService: LoginService
) {
    @PostMapping("/api/v1/login")
    fun loginMember(@RequestBody requestDto: LoginRequest): String {
        return loginService.login(requestDto.accessToken)
    }
}
