package org.inner.circle.o2oserver.member.presentation.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.inner.circle.o2oserver.commons.response.BaseResponse
import org.inner.circle.o2oserver.member.presentation.dto.AddressIdResponse
import org.inner.circle.o2oserver.member.presentation.dto.AddressRequest
import org.inner.circle.o2oserver.member.presentation.dto.GetAddressResponse
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.core.userdetails.UserDetails

@Tag(name = "주소", description = "회원 주소 관리 API")
interface AddressDoc {
    @Operation(summary = "주소 목록 조회", description = "로그인한 회원의 주소 목록을 조회합니다.")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            content = [
                Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = GetAddressResponse::class),
                ),
            ],
            description = "주소 목록 조회 성공",
        ),
    )
    fun getAddresses(userDetails: UserDetails): BaseResponse<GetAddressResponse>

    @Operation(summary = "새 주소 생성", description = "새로운 주소를 생성합니다.")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            content = [
                Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = AddressIdResponse::class),
                ),
            ],
            description = "주소 생성 성공",
        ),
    )
    fun createAddress(createRequest: AddressRequest.CreateAddress, userDetails: UserDetails): BaseResponse<AddressIdResponse>

    @Operation(summary = "기본 주소 설정", description = "기존 주소 중 하나를 기본 주소로 설정합니다.")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            content = [
                Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = AddressIdResponse::class),
                ),
            ],
            description = "기본 주소 설정 성공",
        ),
    )
    fun setMainAddress(addressId: Long, userDetails: UserDetails): BaseResponse<AddressIdResponse>

    @Operation(summary = "주소 삭제", description = "지정된 주소를 삭제합니다.")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            content = [
                Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = AddressIdResponse::class),
                ),
            ],
            description = "주소 삭제 성공",
        ),
    )
    fun deleteAddress(addressId: Long, userDetails: UserDetails): BaseResponse<AddressIdResponse>
}
