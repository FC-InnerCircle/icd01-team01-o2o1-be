package org.inner.circle.o2oserver.member.domain

interface KakaoReader {
    fun getUserInfo(accessToken: String): KakaoUserInfo
}
