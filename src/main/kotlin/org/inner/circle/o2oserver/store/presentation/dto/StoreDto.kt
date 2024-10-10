package org.inner.circle.o2oserver.store.presentation.dto


import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import org.inner.circle.o2oserver.store.domain.review.ReviewInfo
import org.inner.circle.o2oserver.store.domain.store.command.StoreListCommand
import org.springframework.data.domain.PageRequest

data class CommonResponse(
    val response: Any,
    val statusCode: Int,
    val msg: String,
)

data class CommonListResponse(
    val totalCount: Long,
    val page: Int?,
    val size: Int,
    val response: Any,
    val statusCode: Int,
    val msg: String,
)

data class StoreListRequest(
    @field:Positive(message = "위경도 값을 다시 확인해주세요") @Max(value = 38, message = "위경도 값을 다시 확인해주세요")
    val latitude: Double,

    @field:Positive(message = "위경도 값을 다시 확인해주세요") @Max(value = 129, message = "위경도 값을 다시 확인해주세요")
    val longitude: Double,
    val category: String?,
    val page: Int = 0,
    val size: Int = 10,
    val keyword: String = "",
) {
    fun toCommand():StoreListCommand {
        return StoreListCommand(
            pageable = PageRequest.of(page, size),
            longitude = longitude,
            latitude = latitude,
            category = category,
            keyword = keyword
        )
    }
}

data class StoreResponse(
    val stores: List<StoreInfo>,
    val totalCount: Int,
    val page: Int,
    val size: Int,
    val statusCode: Int,
    val msg: String,
)

data class StoreInfo(
    val storeId: Long,
    val storeName: String,
    val minimumPrice: Int,
    val deliveryPrice: Int,
    val reviewCount: Int,
    val reviewRate: Double,
    val thumbnailUrl: String,
    val category: String,
)

data class StoreDetailInfoDTO(
    val storeId: Long,
    val storeName: String,
    val contactNumber: String? = null,
    val zipCode: String? = null,
    val address: String? = null,
    val addressDetail: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val openTime: String? = null,
    val closeTime: String? = null,
    val category: String? = null,
    val deliveryArea: String? = null,
    val deliveryPrice: Int? = null,
    val minimumPrice: Int? = null,
    val reviewCount: Int? = null,
    val reviewRate: Double? = null,
    val thumbnails: List<String>? = null,
    val menus: List<StoreMenuDTO>,
)

data class StoreMenuDTO(
    val menuId: Long?,
    val menuName: String,
    val menuPrice: Long?,
    val optionGroups: List<MenuOptionGroupDTO>?,
    val description: String?,
    val menuImages: List<String>?,
)

class MenuOptionGroupDTO(
    val optionGroupId: Long?,
    val optionGroupName: String?,
    val options: List<MenuOptionDTO>?,
    val isRequired: Boolean?,
    val isMultiple: Boolean?,
)

class MenuOptionDTO(
    val optionId: Long?,
    val optionName: String,
    val optionPrice: Int,
)

data class Review(
    val reviewId: Int,
    val contents: String,
    val rating: Int,
    val reviewImage: List<String>,
)

data class StoreReviewDTO(
    val storeName: String,
    val reviews: List<ReviewInfo>,
)
