package org.inner.circle.o2oserver.store.domain.store

interface StoreReader {
    fun getStoreDetail(storeId: Long): Store
}
