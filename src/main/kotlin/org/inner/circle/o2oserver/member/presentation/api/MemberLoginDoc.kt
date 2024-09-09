package org.inner.circle.o2oserver.member.presentation.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.inner.circle.o2oserver.member.presentation.dto.LoginRequest
import org.inner.circle.o2oserver.member.presentation.dto.LoginResponse
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "회원", description = "회원 로그인, 가입 API")
interface MemberLoginDoc {

    @Operation(summary = "로그인", description = "회원 로그인, 가입 API")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            content = [
                Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = LoginResponse::class),
                ),
            ],
            description = "회원 로그인 완료",
        ),
    )
    fun loginMember(
        @RequestBody loginRequest: LoginRequest.Login,
    ): ResponseEntity<LoginResponse>
}
