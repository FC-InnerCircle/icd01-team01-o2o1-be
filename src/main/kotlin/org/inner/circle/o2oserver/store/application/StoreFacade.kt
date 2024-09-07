package org.inner.circle.o2oserver.store.application

import org.inner.circle.o2oserver.store.domain.store.Store
import org.inner.circle.o2oserver.store.domain.store.StoreListInfo
import org.inner.circle.o2oserver.store.domain.store.StoreService
import org.inner.circle.o2oserver.store.domain.store.command.StoreListCommand
import org.springframework.stereotype.Service

@Service
class StoreFacade(
    private val storeService: StoreService,
) {
    fun getStoreDetail(storeId: Long): Store {
        return storeService.getStoreDetail(storeId)
    }

    fun getStoreList(storeListCommand: StoreListCommand): StoreListInfo {
        return storeService.getStoreList(storeListCommand)
    }
}
