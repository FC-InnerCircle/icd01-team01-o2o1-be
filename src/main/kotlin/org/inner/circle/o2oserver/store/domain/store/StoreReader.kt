package org.inner.circle.o2oserver.store.domain.store

import org.inner.circle.o2oserver.store.domain.store.command.StoreListCommand

interface StoreReader {
    fun getStoreDetail(storeId: Long): Store

    fun getStoreList(command: StoreListCommand): Pair<List<Store>, Int>
}
