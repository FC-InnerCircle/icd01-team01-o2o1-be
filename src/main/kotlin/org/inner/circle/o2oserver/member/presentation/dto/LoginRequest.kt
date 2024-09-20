package org.inner.circle.o2oserver.member.presentation.dto

import jakarta.validation.constraints.NotBlank
import org.inner.circle.o2oserver.member.domain.Member

class LoginRequest {
    data class Login(
        @field:NotBlank(message = "SNS type cannot be blank") val snsType: String,
        @field:NotBlank(message = "Sub ID cannot be blank") val subId: String,
        @field:NotBlank(message = "Name cannot be blank") val name: String,
    ) {
        companion object {
            fun toMember(login: Login): Member {
                return Member(
                    snsType = login.snsType,
                    subId = login.subId,
                    name = login.name,
                )
            }
        }
    }
}
