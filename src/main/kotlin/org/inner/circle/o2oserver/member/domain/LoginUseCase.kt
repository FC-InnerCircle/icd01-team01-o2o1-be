package org.inner.circle.o2oserver.member.domain

interface LoginUseCase {
    fun login(accessToken: String): Member
}
