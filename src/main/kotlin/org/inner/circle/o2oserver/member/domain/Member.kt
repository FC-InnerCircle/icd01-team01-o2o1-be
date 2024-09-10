package org.inner.circle.o2oserver.member.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "members")
class Member(
    @Id val id: String? = null,
    val memberId: Long? = 0,
    val name: String,
    val snsType: String,
    val subId: String,
    val nickName: String? = null,
    val contact: String? = null,
    val status: String? = null,
    val loginStatus: Boolean? = null,
) {
    fun updateMemberInfo(nickName: String, contact: String): Member {
        return Member(
            id = this.id,
            memberId = this.memberId,
            name = this.name,
            snsType = this.snsType,
            subId = this.subId,
            nickName = nickName,
            contact = contact,
            status = this.status,
            loginStatus = this.loginStatus,
        )
    }
}

class MemberDetail(
    val id: String,
    val nickName: String,
    val contact: String,
)
