package org.inner.circle.o2oserver.store.infrastructure.client

import org.inner.circle.o2oserver.store.domain.Menu
import org.inner.circle.o2oserver.store.domain.MenuOption
import org.inner.circle.o2oserver.store.domain.MenuOptionGroup
import org.inner.circle.o2oserver.store.domain.store.Store
import org.inner.circle.o2oserver.store.domain.store.StoreListInfo
import org.inner.circle.o2oserver.store.domain.store.StoreReader
import org.inner.circle.o2oserver.store.domain.store.command.StoreListCommand
import org.inner.circle.o2oserver.store.infrastructure.repository.mongo.MongoRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.geo.Distance
import org.springframework.data.geo.Metrics
import org.springframework.data.geo.Point
import org.springframework.stereotype.Component

@Component
class StoreReadImpl(private val storeApiClient: StoreApiClient, private val mongoRepository: MongoRepository) : StoreReader {
    override fun getStoreDetail(storeId: Long): Store {
        // ## TODO 추후 API 로 구현 예정
        return Store(
            storeId = 1,
            storeName = "홍길동 한식당",
            contactNumber = "02-1234-5678",
            zipCode = "12345",
            address = "경기도성남시수정구",
            addressDetail = "1312호",
            latitude = 37.6,
            longitude = 126.8,
            openTime = "09:00",
            closeTime = "18:00",
            category = "한식",
            deliveryArea = "위례",
            minimumPrice = 15000,
            reviewCount = 10,
            reviewRate = 4.5,
            thumbnails = listOf(),
            menus = listOf(
                Menu(
                    menuId = 101,
                    menuName = "비빔밥",
                    menuPrice = 8000,
                    menuImages = listOf(),
                    description = "신선한 야채와 고소한 참기름이 어우러진 전통 비빔밥",
                    menuCount = 0,
                    menuOptionGroups = listOf(
                        MenuOptionGroup(
                            menuOptionGroupId = 201,
                            menuOptionGroupName = "추가 토핑",
                            menuOptions = listOf(
                                MenuOption(
                                    optionId = 301,
                                    optionName = "계란 추가",
                                    optionPrice = 1000,
                                ),
                                MenuOption(
                                    optionId = 302,
                                    optionName = "고기 추가",
                                    optionPrice = 2000,
                                ),
                            ),
                            isRequired = true,
                            isMultiple = true,
                        ),
                        MenuOptionGroup(
                            menuOptionGroupId = 203,
                            menuOptionGroupName = "추가 토핑2",
                            menuOptions = listOf(
                                MenuOption(
                                    optionId = 303,
                                    optionName = "간장 추가",
                                    optionPrice = 1000,
                                ),
                                MenuOption(
                                    optionId = 304,
                                    optionName = "고추장 추가",
                                    optionPrice = 2000,
                                ),
                            ),
                            isRequired = true,
                            isMultiple = false,
                        ),
                    ),
                ),
            ),
        )
    }

    override fun getStoreList(command: StoreListCommand): StoreListInfo {
        val response = storeApiClient.getStoreList()
        return StoreListInfo(totalCount = response.totalCount, stores = response.stores.map { it.toDomain() })
    }

    override fun getStoreListWithLocationAndName(command: StoreListCommand): StoreListInfo {
        val pageable = PageRequest.of(command.page, command.size)

        val keyword = command.keyword ?: ""
        val (latitude, longitude) = command.address

        val point = Point(longitude, latitude)
        val distance = Distance(5.0, Metrics.KILOMETERS)
        val mongo = mongoRepository.findByStoreNameContainingAndLocationNear(keyword, point, distance, pageable)

        return StoreListInfo(totalCount = mongo.totalElements, stores = mongo.content.map { it.toDomain() })
    }
}
