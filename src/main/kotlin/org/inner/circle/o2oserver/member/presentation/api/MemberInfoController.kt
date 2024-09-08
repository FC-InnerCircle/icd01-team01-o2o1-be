package org.inner.circle.o2oserver.member.presentation.api

import jakarta.servlet.http.HttpServletRequest
import org.inner.circle.o2oserver.commons.response.BaseResponse
import org.inner.circle.o2oserver.commons.security.TokenProvider
import org.inner.circle.o2oserver.member.application.MemberInfoFacade
import org.inner.circle.o2oserver.member.presentation.dto.MemberRequest
import org.inner.circle.o2oserver.member.presentation.dto.MemberResponseData
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/member")
class MemberInfoController(
    private val memberInfoFacade: MemberInfoFacade,
    private val tokenProvider: TokenProvider,
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    fun getMemberInfo(request: HttpServletRequest): BaseResponse {
        val token = tokenProvider.resolveToken(request, "Authorization")
        val authentication = tokenProvider.getAuthentication(token!!)
        log.info("Member ID: ${authentication.name}")
        val member = memberInfoFacade.getMemberInfo(authentication.name)
        val response = MemberResponseData(
            memberId = member.memberId!!,
            name = member.name,
            contact = member.contact ?: "No contract info",
            nickName = member.nickName ?: "No nickname",
        )

        return BaseResponse.success(response)
    }

    @PostMapping
    fun createMemberInfo(
        @RequestBody createRequest: MemberRequest.MemberInfo,
        request: HttpServletRequest,
    ): BaseResponse {
        val token = tokenProvider.resolveToken(request, "Authorization")
        val authentication = tokenProvider.getAuthentication(token!!)
        val memberId = authentication.name
        val memberInfo = MemberRequest.MemberInfo.toMemberDetail(createRequest, memberId)
        val address = MemberRequest.MemberInfo.toAddress(createRequest, memberId)
        memberInfoFacade.createMemberInfo(memberInfo, address)

        return BaseResponse.success(mapOf<String, Any>())
    }

    @DeleteMapping
    fun deleteMember(request: HttpServletRequest): BaseResponse {
        val token = tokenProvider.resolveToken(request, "Authorization")
        val authentication = tokenProvider.getAuthentication(token!!)
        val memberId = authentication.name
        log.info("Delete member ID: $memberId")
        memberInfoFacade.deleteMember(memberId)

        return BaseResponse.success(mapOf<String, Any>())
    }
}
