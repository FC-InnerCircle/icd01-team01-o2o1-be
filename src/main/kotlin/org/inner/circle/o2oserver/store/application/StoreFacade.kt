package org.inner.circle.o2oserver.store.application

import org.inner.circle.o2oserver.store.domain.store.Store
import org.inner.circle.o2oserver.store.domain.store.StoreService
import org.inner.circle.o2oserver.store.domain.store.command.StoreListCommand

class StoreFacade(
    private val storeService: StoreService,
) {
    fun getStoreDetail(storeId: Long): Store {
        return storeService.getStoreDetail(storeId)
    }

    fun getStoreList(storeListCommand: StoreListCommand): Pair<List<Store>, Int> {
        return storeService.getStoreList(storeListCommand)
    }
}
