package org.inner.circle.o2oserver.member.domain

interface MemberStore {
    fun save(member: Member): Member
    fun delete(member: Member)
}
