package org.inner.circle.o2oserver.member.domain

interface LoginUseCase {
    fun findOrCreateMember(member: Member): MemberCreationResult
}

data class MemberCreationResult(
    val member: Member,
    val isSignup: Boolean,
)
