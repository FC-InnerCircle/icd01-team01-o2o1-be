package org.inner.circle.o2oserver.member.presentation.api

import io.github.oshai.kotlinlogging.KotlinLogging
import org.inner.circle.o2oserver.member.application.LoginFacade
import org.inner.circle.o2oserver.member.presentation.dto.LoginRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/login")
class MemberLoginController(
    private val loginFacade: LoginFacade
) {
    private val log = KotlinLogging.logger {}

    @PostMapping
    fun loginMember(@RequestBody requestDto: LoginRequest): String {
        log.info { "loginMember requestDto: $requestDto" }
        return loginFacade.login(requestDto)
    }
}
