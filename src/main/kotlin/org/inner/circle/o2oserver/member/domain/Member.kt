package org.inner.circle.o2oserver.member.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "members")
data class Member(
    @Id val memberId: String? = null,
    val name: String,
    val snsType: String,
    val subId: String,
    val nickName: String? = null,
    val contact: String? = null,
    val status: String? = null,
    val loginStatus: Boolean? = null,
    val addressIds: List<String>? = null,
)
