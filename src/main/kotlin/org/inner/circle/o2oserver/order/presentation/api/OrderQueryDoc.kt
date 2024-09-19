package org.inner.circle.o2oserver.order.presentation.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.flow.Flow
import org.inner.circle.o2oserver.commons.response.BaseResponse
import org.inner.circle.o2oserver.order.presentation.dto.OrderDeliveryResponse
import org.inner.circle.o2oserver.order.presentation.dto.OrderDetailResponse
import org.inner.circle.o2oserver.order.presentation.dto.OrderListResponse
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Tag(name = "주문", description = "주문 생성, 취소, 조회 API")
interface OrderQueryDoc {
    @Operation(summary = "주문 목록 조회", description = "주문 목록을 조회하는 API")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            content = [
                Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = OrderListResponse.OrderListResponse::class),
                ),
            ],
            description = "주문 목록 조회 성공",
        ),
    )
    fun getOrders(
        @AuthenticationPrincipal userDetails: UserDetails,
    ): BaseResponse<OrderListResponse.OrderListResponse>

    @Operation(summary = "주문 상세 조회", description = "주문 상세를 조회하는 API")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            content = [
                Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = OrderListResponse.OrderListResponse::class),
                ),
            ],
            description = "주문 상세 조회 성공",
        ),
    )
    fun getOrder(
        @PathVariable orderId: Long,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): BaseResponse<OrderDetailResponse.OrderInfoDetail>

    @Operation(summary = "배달 상태 구독", description = "배달 상태를 구독하는 API")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            content = [
                Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = OrderListResponse.OrderListResponse::class),
                ),
            ],
            description = "배달 상태 구독 성공",
        ),
    )
    fun deliverySubscribe(
        @PathVariable orderId: Long,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): SseEmitter

    @Operation(summary = "주문 상태 구독", description = "주문 상태를 구독하는 API")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            content = [
                Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = OrderListResponse.OrderListResponse::class),
                ),
            ],
            description = "주문 상태 구독 성공",
        ),
    )
    fun orderStatusSubscribe(orderId: Long, userDetails: UserDetails): SseEmitter
}
