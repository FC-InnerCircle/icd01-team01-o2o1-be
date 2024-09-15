package org.inner.circle.o2oserver.member.presentation.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.inner.circle.o2oserver.member.application.MemberInfoFacade
import org.inner.circle.o2oserver.member.domain.Member
import org.inner.circle.o2oserver.member.presentation.dto.MemberRequest
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class MemberInfoControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var memberInfoFacade: MemberInfoFacade

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    @WithMockUser(username = "605c72b8f9a5b812dcd81458", roles = ["USER"])
    fun getMemberInfo() {
        val mockMember = Member(
            id = "605c72b8f9a5b812dcd81458",
            memberId = 1L,
            name = "John Doe",
            snsType = "test",
            subId = "subId",
            nickName = "Johnny",
            contact = "010-1234-5678",
        )

        `when`(memberInfoFacade.getMemberInfo("605c72b8f9a5b812dcd81458")).thenReturn(mockMember)

        mockMvc.perform(get("/api/v1/member"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.response.memberId").value(1))
            .andExpect(jsonPath("$.response.name").value("John Doe"))
            .andExpect(jsonPath("$.response.contact").value("010-1234-5678"))
            .andExpect(jsonPath("$.response.nickName").value("Johnny"))
    }

    @Test
    @WithMockUser(username = "605c72b8f9a5b812dcd81458", roles = ["USER"])
    fun createMemberInfo() {
        val memberRequest = MemberRequest.MemberInfo(
            nickName = "테스트",
            contact = "010-1234-5678",
            address = MemberRequest.MemberInfo.AddressRequest(
                address = "서울시 강남구",
                addressDetail = "테스트 주소",
                latitude = 37.123456,
                longitude = 127.123456,
                zipCode = "12345",
            ),
        )

        val requestBody = objectMapper.writeValueAsString(memberRequest)

        mockMvc.perform(
            post("/api/v1/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody),
        )
            .andExpect(status().isOk)
    }

    @Test
    @WithMockUser(username = "605c72b8f9a5b812dcd81458", roles = ["USER"])
    fun deleteMember() {
        mockMvc.perform(delete("/api/v1/member"))
            .andExpect(status().isOk)

        verify(memberInfoFacade).deleteMember("605c72b8f9a5b812dcd81458")
    }
}
