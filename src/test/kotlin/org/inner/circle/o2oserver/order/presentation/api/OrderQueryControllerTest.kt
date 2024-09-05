package org.inner.circle.o2oserver.order.presentation.api

import org.inner.circle.o2oserver.order.application.OrderQueryFacade
import org.inner.circle.o2oserver.order.infrastructure.repository.OrderStorage
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class OrderQueryControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var orderStorage: OrderStorage

    @MockBean
    private lateinit var orderQueryFacade: OrderQueryFacade

    @MockBean
    private lateinit var orderQueryController: OrderQueryController

    @Test
    @WithMockUser(username = "hello@gmail.com", password = "1234", roles = ["USER"])
    fun `회원의 주문 리스트를 일괄 조회한다`() {
        val mockRequest =
            mockMvc.perform(
                get("/api/v1/order")
                    .contentType("application/json"),
            )
        println(mockRequest.andReturn().response.contentAsString)
        mockRequest.andExpect(status().isOk)
    }

    @Test
    @WithMockUser(username = "hello@gmail.com", password = "1234", roles = ["USER"])
    fun `회원의 주문 상세정보를 조회한다`() {

        val mockRequest =
            mockMvc.perform(
                get("/api/v1/order/1")
                    .contentType("application/json"),
            )
        println(mockRequest.andReturn().response.contentAsString)
        mockRequest.andExpect(status().isOk)
    }
}
