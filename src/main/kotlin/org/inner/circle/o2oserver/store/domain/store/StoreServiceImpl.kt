package org.inner.circle.o2oserver.store.domain.store

import org.inner.circle.o2oserver.store.domain.store.command.StoreListCommand
import org.springframework.stereotype.Service

@Service
class StoreServiceImpl(
    val storeReader: StoreReader,
) : StoreService {
    override fun getStoreDetail(storeId: Long): Store {
        return storeReader.getStoreDetail(storeId)
    }

    override fun getStoreList(command: StoreListCommand): Pair<List<Store>, Int> {
        return storeReader.getStoreList(
            command,
        )
    }
}
