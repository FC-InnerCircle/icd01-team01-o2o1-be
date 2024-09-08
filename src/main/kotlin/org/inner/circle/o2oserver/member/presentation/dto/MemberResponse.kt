package org.inner.circle.o2oserver.member.presentation.dto

data class MemberResponseData(
    val memberId: Long,
    val name: String,
    val contact: String,
    val nickName: String,
)
