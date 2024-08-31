package org.inner.circle.o2oserver.member.domain

interface MemberReader {
    fun getMember(): Optional<Member>
}
