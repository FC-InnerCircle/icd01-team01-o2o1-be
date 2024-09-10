package org.inner.circle.o2oserver.store.domain.store

import org.inner.circle.o2oserver.store.domain.store.command.StoreListCommand

interface StoreService {
    // Command 는 명령, Criteria 는 조회 성격, Info 는 객체에 대한 Return
    fun getStoreDetail(storeId: Long): Store

    fun getStoreList(command: StoreListCommand): StoreListInfo

    fun getStoreListWithLocationAndName(command: StoreListCommand): StoreListInfo
}
