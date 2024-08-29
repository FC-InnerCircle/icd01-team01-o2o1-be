package org.inner.circle.o2oserver.member.infrastructure.client

import io.github.oshai.kotlinlogging.KotlinLogging
import org.inner.circle.o2oserver.member.domain.KakaoReader
import org.inner.circle.o2oserver.member.domain.KakaoUserInfo
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class KakaoClient(
    private val restClient: RestClient
) : KakaoReader {
    private val log = KotlinLogging.logger {}

    override fun getUserInfo(accessToken: String): KakaoUserInfo {
        // access token을 이용하여 카카오 사용자 정보를 가져온다.
        // 카카오 사용자 정보를 KakaoUserInfo로 변환하여 반환한다.
        val headers = HttpHeaders().apply {
            set("Authorization", "Bearer $accessToken")
        }
        val response =
            restClient.get().uri("https://kapi.kakao.com/v2/user/me").headers { it.addAll(headers) }
                .retrieve().body(Map::class.java)

        println(response)

        return KakaoUserInfo(
            kakaoId = "sss",
            email = "ddd@gmail.com",
            userName = "sdsd",
        )
    }
}
