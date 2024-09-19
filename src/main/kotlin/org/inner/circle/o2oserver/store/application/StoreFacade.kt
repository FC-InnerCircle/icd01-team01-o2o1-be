package org.inner.circle.o2oserver.store.application

import org.inner.circle.o2oserver.store.domain.store.StoreListInfo
import org.inner.circle.o2oserver.store.domain.store.StoreService
import org.inner.circle.o2oserver.store.domain.store.command.StoreListCommand
import org.inner.circle.o2oserver.store.presentation.dto.MenuOptionDTO
import org.inner.circle.o2oserver.store.presentation.dto.MenuOptionGroupDTO
import org.inner.circle.o2oserver.store.presentation.dto.StoreDetailInfoDTO
import org.inner.circle.o2oserver.store.presentation.dto.StoreMenuDTO
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class StoreFacade(
    private val storeService: StoreService,
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun getStoreDetail(storeId: Long): StoreDetailInfoDTO {
        val store = storeService.getStoreDetail(storeId)
        return StoreDetailInfoDTO(
            storeId = store.storeId,
            storeName = store.storeName,
            contactNumber = store.contactNumber,
            zipCode = store.zipCode,
            address = store.address,
            addressDetail = store.addressDetail,
            latitude = store.latitude,
            longitude = store.latitude,
            openTime = store.openTime,
            closeTime = store.closeTime,
            category = store.category,
            deliveryArea = store.deliveryArea,
            deliveryPrice = store.deliveryPrice,
            minimumPrice = store.minimumPrice,
            reviewCount = store.reviewCount,
            reviewRate = store.reviewRate,
            thumbnails = store.thumbnails,
            menus = store.menus.map {
                StoreMenuDTO(
                    menuId = it.menuId,
                    menuName = it.menuName,
                    menuPrice = it.menuPrice,
                    optionGroups = it.menuOptionGroups?.map {
                        MenuOptionGroupDTO(
                            optionGroupId = it.menuOptionGroupId,
                            optionGroupName = it.menuOptionGroupName,
                            options = it.menuOptions?.map {
                                MenuOptionDTO(
                                    optionId = it.optionId,
                                    optionName = it.optionName,
                                    optionPrice = it.optionPrice,
                                )
                            },
                            isRequired = it.isRequired,
                            isMultiple = it.isMultiple,
                        )
                    },
                    description = it.description,
                    menuImages = it.menuImages,
                )
            },
        )
    }

    fun getStoreList(storeListCommand: StoreListCommand): StoreListInfo {
        return storeService.getStoreListWithLocationAndName(storeListCommand)
    }
}
