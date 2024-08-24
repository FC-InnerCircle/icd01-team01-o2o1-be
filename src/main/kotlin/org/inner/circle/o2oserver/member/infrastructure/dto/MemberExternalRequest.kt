package org.inner.circle.o2oserver.member.infrastructure.dto

class MemberExternalRequest {
    data class MemberInfo(val id: String, val name: String, val email: String)
}