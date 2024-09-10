package org.inner.circle.o2oserver.member.presentation.api

import org.inner.circle.o2oserver.member.application.LoginFacade
import org.inner.circle.o2oserver.member.presentation.dto.LoginRequest
import org.inner.circle.o2oserver.member.presentation.dto.LoginResponse
import org.inner.circle.o2oserver.member.presentation.dto.LoginResponseData
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/login")
class MemberLoginController(
    private val loginFacade: LoginFacade,
) : MemberLoginDoc {
    private val log = LoggerFactory.getLogger(this::class.java)

    @PostMapping
    override fun loginMember(
        @RequestBody loginRequest: LoginRequest.Login,
    ): ResponseEntity<LoginResponse> {
        log.info("login 요청")
        val member = LoginRequest.Login.toMember(loginRequest)
        val (jwtToken, isSignup) = loginFacade.login(member)

        val headers = HttpHeaders().apply {
            set("Authorization", jwtToken.accessToken)
            set("RefreshAuth", jwtToken.refreshToken)
        }

        val responseBody = LoginResponse(
            response = LoginResponseData(isSignup = isSignup),
            statusCode = HttpStatus.OK.value(),
            msg = "회원 로그인 완료",
        )

        return ResponseEntity(responseBody, headers, HttpStatus.OK)
    }
}
