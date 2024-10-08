package org.inner.circle.o2oserver.member.presentation.api

import jakarta.validation.Valid
import org.inner.circle.o2oserver.commons.response.BaseResponse
import org.inner.circle.o2oserver.member.application.MemberInfoFacade
import org.inner.circle.o2oserver.member.presentation.dto.AddressIdResponse
import org.inner.circle.o2oserver.member.presentation.dto.AddressRequest
import org.inner.circle.o2oserver.member.presentation.dto.AddressResponseItem
import org.inner.circle.o2oserver.member.presentation.dto.GetAddressResponse
import org.slf4j.LoggerFactory
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
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
) : AddressDoc {
    private val log = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    override fun getAddresses(
        @AuthenticationPrincipal userDetails: UserDetails,
    ): BaseResponse<GetAddressResponse> {
        log.info("Get Addresses Member ID: ${userDetails.username}")
        val addresses = memberInfoFacade.getAddresses(userDetails.username)
        val addressResponseData = GetAddressResponse(
            addresses = addresses.map { address ->
                AddressResponseItem(
                    addressId = address.addressId!!,
                    address = address.address,
                    addressDetail = address.addressDetail,
                    latitude = address.latitude,
                    longitude = address.longitude,
                    zipCode = address.zipCode,
                    addressStatus = if (address.isDefault) "main" else "sub",
                )
            },
        )

        return BaseResponse.success(addressResponseData)
    }

    @PostMapping
    override fun createAddress(
        @RequestBody @Valid createRequest: AddressRequest.CreateAddress,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): BaseResponse<AddressIdResponse> {
        val memberId = userDetails.username
        val addressInfo = AddressRequest.CreateAddress.toAddress(createRequest, memberId)
        val newAddress = memberInfoFacade.createAddress(addressInfo)
        val response = newAddress.addressId?.let { AddressIdResponse(it) } ?: AddressIdResponse(-1)

        return BaseResponse.success(response)
    }

    @PutMapping("/{addressId}")
    override fun setMainAddress(
        @PathVariable addressId: Long,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): BaseResponse<AddressIdResponse> {
        val memberId = userDetails.username
        log.info("Setting main address for member ID: $memberId, address ID: $addressId")

        memberInfoFacade.setDefaultAddress(memberId, addressId)

        return BaseResponse.success(AddressIdResponse(addressId))
    }

    @DeleteMapping("/{addressId}")
    override fun deleteAddress(
        @PathVariable addressId: Long,
        @AuthenticationPrincipal userDetails: UserDetails,
    ): BaseResponse<AddressIdResponse> {
        log.info("Delete address for address ID: $addressId")
        memberInfoFacade.deleteAddress(addressId)

        return BaseResponse.success(AddressIdResponse(addressId))
    }
}
