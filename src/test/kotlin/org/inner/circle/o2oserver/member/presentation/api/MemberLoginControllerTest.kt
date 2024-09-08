package org.inner.circle.o2oserver.member.presentation.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.inner.circle.o2oserver.member.presentation.dto.LoginRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class MemberLoginControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    lateinit var loginRequest: LoginRequest.Login

    @BeforeEach
    fun setUp() {
        loginRequest = LoginRequest.Login(
            snsType = "kakao",
            subId = "1234567890",
            name = "홍길동",
        )
    }

    @Test
    fun loginMember() {
        val asString = objectMapper.writeValueAsString(loginRequest)

        mockMvc.perform(
            post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asString),
        )
            .andExpect(status().isOk)
            .andExpect(header().exists("Authorization")) // Authorization 헤더가 존재하는지 확인
            .andExpect(header().exists("RefreshAuth")) // RefreshAuth 헤더가 존재하는지 확인
            .andDo(MockMvcResultHandlers.print())
    }
}
