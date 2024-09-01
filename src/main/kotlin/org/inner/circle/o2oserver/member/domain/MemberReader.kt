package org.inner.circle.o2oserver.member.domain

interface MemberReader {
    fun getMemberById(memberId: String): Member?

    fun findBySnsTypeAndSubId(snsType: String, subId: String): Member?
}
