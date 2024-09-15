package org.inner.circle.o2oserver.member.presentation.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.inner.circle.o2oserver.member.application.MemberInfoFacade
import org.inner.circle.o2oserver.member.domain.Address
import org.inner.circle.o2oserver.member.presentation.dto.AddressRequest
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class AddressControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var memberInfoFacade: MemberInfoFacade

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    @WithMockUser(username = "605c72b8f9a5b812dcd81458", roles = ["USER"])
    fun getAddresses() {
        val mockAddress = Address(
            memberId = "605c72b8f9a5b812dcd81458",
            address = "Seoul",
            addressDetail = "Some detail",
            latitude = 37.5665,
            longitude = 126.9780,
            zipCode = "12345",
            isDefault = true,
        )

        `when`(memberInfoFacade.getAddresses("605c72b8f9a5b812dcd81458")).thenReturn(listOf(mockAddress))

        mockMvc.perform(get("/api/v1/address"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.response.addresses[0].address").value("Seoul"))
            .andExpect(jsonPath("$.response.addresses[0].latitude").value(37.5665))
            .andExpect(jsonPath("$.response.addresses[0].longitude").value(126.9780))
    }

    @Test
    @WithMockUser(username = "605c72b8f9a5b812dcd81458", roles = ["USER"])
    fun createAddress() {
        val createAddressRequest = AddressRequest.CreateAddress(
            address = "Seoul",
            addressDetail = "Some detail",
            latitude = 37.5665,
            longitude = 126.9780,
            zipCode = "12345",
        )

        val requestBody = objectMapper.writeValueAsString(createAddressRequest)

        mockMvc.perform(
            post("/api/v1/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody),
        )
            .andExpect(status().isOk)
    }

    @Test
    @WithMockUser(username = "605c72b8f9a5b812dcd81458", roles = ["USER"])
    fun setMainAddress() {
        mockMvc.perform(put("/api/v1/address/1"))
            .andExpect(status().isOk)

        verify(memberInfoFacade).setDefaultAddress("605c72b8f9a5b812dcd81458", 1L)
    }

    @Test
    @WithMockUser(username = "605c72b8f9a5b812dcd81458", roles = ["USER"])
    fun deleteAddress() {
        mockMvc.perform(delete("/api/v1/address/1"))
            .andExpect(status().isOk)

        verify(memberInfoFacade).deleteAddress(1L)
    }
}
