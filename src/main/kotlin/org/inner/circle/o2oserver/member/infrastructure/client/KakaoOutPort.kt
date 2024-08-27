package org.inner.circle.o2oserver.member.infrastructure.client

import org.inner.circle.o2oserver.member.infrastructure.dto.KakaoUserInfo

interface KakaoOutPort {
    fun getUserInfo(accessToken: String): KakaoUserInfo
}
