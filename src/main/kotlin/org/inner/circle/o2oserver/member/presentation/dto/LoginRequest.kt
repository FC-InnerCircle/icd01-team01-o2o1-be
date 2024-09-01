package org.inner.circle.o2oserver.member.presentation.dto

import org.inner.circle.o2oserver.member.domain.Member

class LoginRequest {
    data class Login(
        val snsType: String,
        val subId: String,
        val email: String,
        val name: String,
    ) {
        companion object {
            fun toMember(login: Login): Member {
                return Member(
                    snsType = login.snsType,
                    subId = login.subId,
                    email = login.email,
                    name = login.name,
                )
            }
        }
    }
}
