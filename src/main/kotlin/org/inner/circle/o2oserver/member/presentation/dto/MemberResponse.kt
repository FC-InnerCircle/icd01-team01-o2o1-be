package org.inner.circle.o2oserver.member.presentation.dto

data class MemberResponse(
    val response: MemberResponseData,
    val statusCode: Int,
    val msg: String,
)

data class MemberResponseData(
    val memberId: Long,
    val name: String,
    val contact: String,
    val nickName: String,
)
