package org.inner.circle.o2oserver.member.presentation.api

import jakarta.servlet.http.HttpServletRequest
import org.inner.circle.o2oserver.commons.security.TokenProvider
import org.inner.circle.o2oserver.member.application.MemberInfoFacade
import org.inner.circle.o2oserver.member.presentation.dto.AddressRequest
import org.inner.circle.o2oserver.member.presentation.dto.AddressResponse
import org.inner.circle.o2oserver.member.presentation.dto.AddressResponseItem
import org.inner.circle.o2oserver.member.presentation.dto.GetAddressResponseData
import org.inner.circle.o2oserver.member.presentation.dto.PostAddressResponseData
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/address")
class AddressController(
    private val memberInfoFacade: MemberInfoFacade,
    private val tokenProvider: TokenProvider,
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    fun getAddresses(request: HttpServletRequest): ResponseEntity<AddressResponse<GetAddressResponseData>> {
        val token = tokenProvider.resolveToken(request, "Authorization")
        val authentication = tokenProvider.getAuthentication(token!!)
        log.info("Get Addresses Member ID: ${authentication.name}")

        val addresses = memberInfoFacade.getAddresses(authentication.name)
        val addressResponseData = GetAddressResponseData(
            addresses = addresses.map { address ->
                AddressResponseItem(
                    addressId = address.addressId!!,
                    address = address.address,
                    detail = address.detail,
                    latitude = address.latitude,
                    longitude = address.longitude,
                    zipCode = address.zipCode,
                    addressStatus = if (address.isDefault) "main" else "sub",
                )
            },
        )
        val responseBody = AddressResponse(
            response = addressResponseData,
            statusCode = HttpStatus.OK.value(),
            msg = "주소 정보를 조회하였습니다.",
        )

        return ResponseEntity(responseBody, HttpStatus.OK)
    }

    @PostMapping
    fun createAddress(
        @RequestBody createRequest: AddressRequest.CreateAddress,
        request: HttpServletRequest,
    ): ResponseEntity<AddressResponse<PostAddressResponseData>> {
        val token = tokenProvider.resolveToken(request, "Authorization")
        val authentication = tokenProvider.getAuthentication(token!!)
        val memberId = authentication.name
        val addressInfo = AddressRequest.CreateAddress.toAddress(createRequest, memberId)
        val newAddress = memberInfoFacade.createAddress(addressInfo)
        val responseBody = AddressResponse(
            response = newAddress.addressId?.let { PostAddressResponseData(it) } ?: PostAddressResponseData(-1),
            statusCode = HttpStatus.OK.value(),
            msg = "주소를 성공적으로 추가했습니다.",
        )

        return ResponseEntity(responseBody, HttpStatus.OK)
    }

    @PutMapping("/{addressId}")
    fun setMainAddress(
        @PathVariable addressId: Long,
        request: HttpServletRequest,
    ): ResponseEntity<AddressResponse<Map<String, Long>>> {
        val token = tokenProvider.resolveToken(request, "Authorization")
        val authentication = tokenProvider.getAuthentication(token!!)
        val memberId = authentication.name
        log.info("Setting main address for member ID: $memberId, address ID: $addressId")

        memberInfoFacade.setDefaultAddress(memberId, addressId)

        val responseBody = AddressResponse(
            response = mapOf("addressId" to addressId),
            statusCode = HttpStatus.OK.value(),
            msg = "메인 주소 설정 완료",
        )

        return ResponseEntity(responseBody, HttpStatus.OK)
    }

    @DeleteMapping("/{addressId}")
    fun deleteAddress(
        @PathVariable addressId: Long,
        request: HttpServletRequest,
    ): ResponseEntity<AddressResponse<Map<String, Long>>> {
        log.info("Delete address for address ID: $addressId")
        memberInfoFacade.deleteAddress(addressId)

        val responseBody = AddressResponse(
            response = mapOf("addressId" to addressId),
            statusCode = HttpStatus.OK.value(),
            msg = "주소를 성공적으로 삭제했습니다.",
        )

        return ResponseEntity(responseBody, HttpStatus.OK)
    }
}
