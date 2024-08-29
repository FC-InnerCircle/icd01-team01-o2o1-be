package org.inner.circle.o2oserver.member.domain

import org.springframework.stereotype.Service

@Service
class LoginService(
    private val kakaoReader: KakaoReader
) {
    fun login(accessToken: String): Member {
        val kakaoUserInfo = kakaoReader.getUserInfo(accessToken)
        // 카카오 유저 정보가져오고
        // 2팀 서버에 저장된 유저 정보와 비교하여 로그인 또는 회원가입 처리
        // 로그인 성공시 토큰 발급하여 유저 정보와 함꼐 반환
        // 마지막으로 로그인한 사용자 정보를 클라이언트로 반환
        return null
    }
}
