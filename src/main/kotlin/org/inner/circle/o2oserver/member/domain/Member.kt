package org.inner.circle.o2oserver.member.domain

data class Member(
    val memberId: Long? = null,
    val name: String,
    val nickName: String,
    val email: String,
    val contact: String,
    val status: String,
    val loginStatus: Boolean,
    val address: List<Address>
)
