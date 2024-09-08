package org.inner.circle.o2oserver.order.presentation.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.inner.circle.o2oserver.commons.response.BaseResponse
import org.inner.circle.o2oserver.order.presentation.dto.OrderCancelResponse
import org.inner.circle.o2oserver.order.presentation.dto.OrderCreateRequest
import org.inner.circle.o2oserver.order.presentation.dto.OrderCreateResponse
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "주문", description = "주문 생성, 취소, 조회 API")
interface OrderCommandDoc {

    @Operation(summary = "주문 생성", description = "주문을 생성하는 API")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            content = [
                io.swagger.v3.oas.annotations.media.Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = OrderCreateResponse.OrderCreateResult::class)
                )
            ],
            description = "주문 생성 성공"),
    )
    fun createOrder(
        @RequestBody orderCreate: OrderCreateRequest.OrderCreate,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): BaseResponse

    @Operation(summary = "주문 취소", description = "주문을 취소하는 API")
    @ApiResponses(
        ApiResponse(responseCode = "200",
            content = [
                io.swagger.v3.oas.annotations.media.Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = OrderCancelResponse.OrderCancel::class)
                )
            ],
            description = "주문 생성 성공"),
    )
    fun cancelOrder(
        @PathVariable orderId: Long,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): BaseResponse
}
