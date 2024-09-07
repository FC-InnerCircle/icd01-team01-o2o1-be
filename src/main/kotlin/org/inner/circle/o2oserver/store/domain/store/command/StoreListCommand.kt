package org.inner.circle.o2oserver.store.domain.store.command

import org.inner.circle.o2oserver.store.domain.Address

class StoreListCommand(
    val address: Address,
    val category: String?,
    val keyword: String?,
    val page: Int?,
    val size: Int?,
)
