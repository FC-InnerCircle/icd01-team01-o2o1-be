package org.inner.circle.o2oserver.member.presentation.api

import jakarta.servlet.http.HttpServletRequest
import org.inner.circle.o2oserver.commons.security.TokenProvider
import org.inner.circle.o2oserver.member.application.MemberInfoFacade
import org.inner.circle.o2oserver.member.presentation.dto.MemberRequest
import org.inner.circle.o2oserver.member.presentation.dto.MemberResponse
import org.inner.circle.o2oserver.member.presentation.dto.MemberResponseData
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
class MemberInfoController(
    private val memberInfoFacade: MemberInfoFacade,
    private val tokenProvider: TokenProvider,
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    fun getMemberInfo(request: HttpServletRequest): ResponseEntity<MemberResponse> {
        val token = tokenProvider.resolveToken(request, "Authorization")
        val authentication = tokenProvider.getAuthentication(token!!)
        log.info("Member ID: ${authentication.name}")
        val member = memberInfoFacade.getMemberInfo(authentication.name)
        val responseBody = MemberResponse(
            response = MemberResponseData(
                memberId = member.memberId!!,
                name = member.name,
                contact = member.contact ?: "No contract info",
                nickName = member.nickName ?: "No nickname",
            ),
            statusCode = HttpStatus.OK.value(),
            msg = "회원 정보를 조회하였습니다",
        )

        return ResponseEntity(responseBody, HttpStatus.OK)
    }

    @PostMapping
    fun createMemberInfo(
        @RequestBody createRequest: MemberRequest.MemberInfo,
        request: HttpServletRequest,
    ): ResponseEntity<MemberResponse> {
        val token = tokenProvider.resolveToken(request, "Authorization")
        val authentication = tokenProvider.getAuthentication(token!!)
        val memberId = authentication.name
        val memberInfo = MemberRequest.MemberInfo.toMemberDetail(createRequest, memberId)
        val address = MemberRequest.MemberInfo.toAddress(createRequest, memberId)
        memberInfoFacade.createMemberInfo(memberInfo, address)
        val responseBody = MemberResponse(
            response = null,
            statusCode = HttpStatus.OK.value(),
            msg = "가입 완료 되었습니다.",
        )

        return ResponseEntity(responseBody, HttpStatus.OK)
    }
}
