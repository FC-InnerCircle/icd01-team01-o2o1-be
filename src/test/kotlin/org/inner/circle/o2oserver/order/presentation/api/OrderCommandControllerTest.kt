package org.inner.circle.o2oserver.order.presentation.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.inner.circle.o2oserver.order.presentation.dto.OrderCreateRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class OrderCommandControllerTest {
    @Autowired lateinit var mockMvc: MockMvc

    @Autowired lateinit var objectMapper: ObjectMapper

    lateinit var orderCreate: OrderCreateRequest.OrderCreate
    lateinit var menuCreate: OrderCreateRequest.MenuCreate
    lateinit var optionGroupCreate: OrderCreateRequest.OptionGroupCreate
    lateinit var optionCreate: OrderCreateRequest.OptionCreate

    @BeforeEach
    fun setUp() {
        optionCreate =
            OrderCreateRequest.OptionCreate(
                optionId = 1L,
            )
        optionGroupCreate = OrderCreateRequest.OptionGroupCreate(
            optionGroupId = 1L,
            options = listOf(optionCreate),
        )
        menuCreate =
            OrderCreateRequest.MenuCreate(
                menuId = 1L,
                menuCount = 2,
                optionGroups = listOf(optionGroupCreate),
            )
        orderCreate =
            OrderCreateRequest.OrderCreate(
                storeId = 1L,
                menus = listOf(menuCreate),
                orderPrice = 10000L,
                payment = "CARD",
                addressId = 1L,
            )
    }

    @Test
    @WithMockUser(username = "hello@gmail.com", password = "1234", roles = ["USER"])
    fun createOrder() {
        val asString = objectMapper.writeValueAsString(orderCreate)

        mockMvc.perform(
            post("/api/v1/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asString),
        )
            .andExpect(status().isOk)
            .andDo(MockMvcResultHandlers.print())
    }
}
