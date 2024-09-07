package org.inner.circle.o2oserver.store.presentation.api

import org.inner.circle.o2oserver.store.application.StoreFacade
import org.inner.circle.o2oserver.store.domain.review.ReviewQueryObject
import org.inner.circle.o2oserver.store.domain.review.ReviewService
import org.inner.circle.o2oserver.store.domain.store.Store
import org.inner.circle.o2oserver.store.domain.store.command.StoreListCommand
import org.inner.circle.o2oserver.store.presentation.dto.CommonListResponse
import org.inner.circle.o2oserver.store.presentation.dto.CommonResponse
import org.inner.circle.o2oserver.store.presentation.dto.StoreListRequest
import org.inner.circle.o2oserver.store.presentation.dto.StoreReviewDTO
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/store")
class StoreController(
    private val storeFacade: StoreFacade,
    private val reviewService: ReviewService,
) {
    @GetMapping("/{storeId}")
    fun getStoreDetail(
        @PathVariable storeId: Long,
    ): CommonResponse<Store> {
        val store: Store = storeFacade.getStoreDetail(storeId)
        return CommonResponse(response = store, msg = "조회 되었습니다", statusCode = 200)
    }

    @PostMapping("")
    fun getStoreDetail(
        @RequestBody request: StoreListRequest,
    ): CommonListResponse<List<Store>> {
        val command =
            StoreListCommand(
                address = request.address.toDomain(),
                category = request.category,
                keyword = request.keyword,
                page = request.page,
                size = request.size,
            )

        val (stores, totalCount) = storeFacade.getStoreList(command)
        return CommonListResponse(
            response = stores,
            totalCount = totalCount,
            size = request.size ?: 0,
            page = request.page,
            statusCode = 200,
            msg = "음식점 목록을 조회했습니다.",
        )
    }

    @GetMapping("/{storeId}/reviews")
    fun getStoreDetail(
        @PathVariable("storeId") storeId: Int,
        @RequestParam("page") page: Int,
        @RequestParam("limit") size: Int,
    ): CommonListResponse<StoreReviewDTO> {
        val queryObject =
            ReviewQueryObject(
                storeId = storeId,
                page = page,
                limit = size,
            )

        val reviews = reviewService.getStoreReviewList(queryObject)
        return CommonListResponse(
            response =
                StoreReviewDTO(
                    reviews = reviews,
                    storeName = "임시 가게 이름",
                ),
            totalCount = 100,
            size = size,
            page = page,
            statusCode = 200,
            msg = "목록을 조회했습니다.",
        )
    }
}
