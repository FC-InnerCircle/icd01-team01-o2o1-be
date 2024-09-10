package org.inner.circle.o2oserver.member.presentation.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.inner.circle.o2oserver.commons.response.BaseResponse
import org.inner.circle.o2oserver.member.presentation.dto.MemberInfoResponse
import org.inner.circle.o2oserver.member.presentation.dto.MemberRequest
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "회원", description = "회원 정보 API")
interface MemberInfoDoc {
    @Operation(summary = "회원 정보 조회", description = "회원 정보를 조회하는 API")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            content = [
                Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = MemberInfoResponse::class),
                ),
            ],
            description = "회원 정보 조회 성공",
        ),
    )
    fun getMemberInfo(
        @AuthenticationPrincipal userDetails: UserDetails,
    ): BaseResponse

    @Operation(summary = "회원 정보 생성", description = "회원 정보를 생성하는 API")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            content = [
                Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = Map::class),
                ),
            ],
            description = "회원 정보 생성 성공",
        ),
    )
    fun createMemberInfo(
        @RequestBody createRequest: MemberRequest.MemberInfo,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): BaseResponse

    @Operation(summary = "회원 정보 삭제", description = "회원 정보를 삭제하는 API")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            content = [
                Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = Map::class),
                ),
            ],
            description = "회원 정보 삭제 성공",
        ),
    )
    fun deleteMember(
        @AuthenticationPrincipal userDetails: UserDetails,
    ): BaseResponse
}
