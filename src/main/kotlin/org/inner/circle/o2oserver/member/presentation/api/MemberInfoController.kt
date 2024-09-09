package org.inner.circle.o2oserver.member.presentation.api

import org.inner.circle.o2oserver.commons.response.BaseResponse
import org.inner.circle.o2oserver.member.application.MemberInfoFacade
import org.inner.circle.o2oserver.member.presentation.dto.MemberInfoResponse
import org.inner.circle.o2oserver.member.presentation.dto.MemberRequest
import org.slf4j.LoggerFactory
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
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
): MemberInfoDoc {
    private val log = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    override fun getMemberInfo(@AuthenticationPrincipal userDetails: UserDetails): BaseResponse {
        log.info("User ID: ${userDetails.username}")
        val member = memberInfoFacade.getMemberInfo(userDetails.username)
        val response = MemberInfoResponse(
            memberId = member.memberId!!,
            name = member.name,
            contact = member.contact ?: "No contract info",
            nickName = member.nickName ?: "No nickname",
        )

        return BaseResponse.success(response)
    }

    @PostMapping
    override fun createMemberInfo(
        @RequestBody createRequest: MemberRequest.MemberInfo,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): BaseResponse {
        val memberId = userDetails.username
        val memberInfo = MemberRequest.MemberInfo.toMemberDetail(createRequest, memberId)
        val address = MemberRequest.MemberInfo.toAddress(createRequest, memberId)
        memberInfoFacade.createMemberInfo(memberInfo, address)

        return BaseResponse.success(mapOf<String, Any>())
    }

    @DeleteMapping
    override fun deleteMember(@AuthenticationPrincipal userDetails: UserDetails): BaseResponse {
        val memberId = userDetails.username
        log.info("Delete member ID: $memberId")
        memberInfoFacade.deleteMember(memberId)

        return BaseResponse.success(mapOf<String, Any>())
    }
}
