package org.inner.circle.o2oserver.member.presentation.dto

data class MemberInfoResponse(
    val memberId: Long,
    val name: String,
    val contact: String,
    val nickName: String,
)

data class MemberIdResponse(
    val memberId: Long,
)
