package org.inner.circle.o2oserver.store.presentation.api

import org.inner.circle.o2oserver.store.domain.store.Store
import org.inner.circle.o2oserver.store.domain.store.StoreService
import org.inner.circle.o2oserver.store.presentation.dto.CommonResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/store")
class StoreController(private val storeService: StoreService) {
    @GetMapping("/{storeId}")
    fun getStoreDetail(
        @PathVariable storeId: Long,
    ): CommonResponse<Store> {
        val store: Store = storeService.getStoreDetail(storeId)
        return CommonResponse(response = store, msg = "조회 되었습니다", statusCode = 200)
    }
}
