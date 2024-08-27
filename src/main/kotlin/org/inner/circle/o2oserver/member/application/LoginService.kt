package org.inner.circle.o2oserver.member.application

import org.inner.circle.o2oserver.member.infrastructure.client.KakaoClient
import org.springframework.stereotype.Service

@Service
class LoginService(
    private val kakaoClient: KakaoClient
) {
    fun login(accessToken: String): String {
        val kakaoUserInfo = kakaoClient.getUserInfo(accessToken)
        println(kakaoUserInfo)
        // 카카오 유저 정보가져오고
        // 2팀 서버에 저장된 유저 정보와 비교하여 로그인 또는 회원가입 처리

        return "login success"
    }
}
