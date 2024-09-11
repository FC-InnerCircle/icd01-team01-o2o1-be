package org.inner.circle.o2oserver.order.presentation.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.inner.circle.o2oserver.order.application.OrderCommandFacade
import org.inner.circle.o2oserver.order.infrastructure.repository.OrderStorage
import org.inner.circle.o2oserver.order.presentation.dto.OrderCreateRequest
import org.inner.circle.o2oserver.order.presentation.dto.OrderReviewRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class OrderCommandControllerTest {
    @Autowired lateinit var mockMvc: MockMvc

    @Autowired lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var orderStorage: OrderStorage

    @MockBean
    private lateinit var orderCommandFacade: OrderCommandFacade

    @MockBean
    private lateinit var orderCommandController: OrderCommandController

    lateinit var orderCreate: OrderCreateRequest.OrderCreate
    lateinit var menuCreate: OrderCreateRequest.MenuCreate
    lateinit var optionGroupCreate: OrderCreateRequest.OptionGroupCreate
    lateinit var optionCreate: OrderCreateRequest.OptionCreate

    @BeforeEach
    fun setUp() {
        optionCreate =
            OrderCreateRequest.OptionCreate(
                optionId = 1L,
                optionName = "optionName",
                optionPrice = 1000L,
            )
        optionGroupCreate = OrderCreateRequest.OptionGroupCreate(
            optionGroupId = 1L,
            options = listOf(optionCreate),
            optionName = "optionGroupName",
        )
        menuCreate = OrderCreateRequest.MenuCreate(
            menuId = 1L,
            menuCount = 2,
            optionGroups = listOf(optionGroupCreate),
            menuName = "menuName",
            menuPrice = 1000L,
        )
        orderCreate = OrderCreateRequest.OrderCreate(
            storeId = 1L,
            storeName = "storeName",
            menus = listOf(menuCreate),
            orderPrice = 10000L,
            payment = "CARD",
            address = OrderCreateRequest.AddressCreate(
                addressId = 1L,
                address = "address",
                addressDetail = "addressDetail",
                zipCode = "01234",
                latitude = 1.0,
                longitude = 1.0,
            ),
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

    @Test
    @WithMockUser(username = "hello@gmail.com", password = "1234", roles = ["USER"])
    fun cancelOrder() {
        mockMvc.perform(
            delete("/api/v1/order/1")
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpect(status().isOk)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @WithMockUser(username = "hello@gmail.com", password = "1234", roles = ["USER"])
    fun createReview() {
        val reviewCreate = OrderReviewRequest.ReviewCreate(
            content = "review",
            rating = 5,
            reviewImage = emptyList(),
        )
        val asString = objectMapper.writeValueAsString(reviewCreate)

        mockMvc.perform(
            post("/api/v1/order/1/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asString),
        )
            .andExpect(status().isOk)
            .andDo(MockMvcResultHandlers.print())
    }
}
