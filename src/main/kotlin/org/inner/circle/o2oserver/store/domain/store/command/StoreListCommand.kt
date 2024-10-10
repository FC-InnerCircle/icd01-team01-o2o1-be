package org.inner.circle.o2oserver.store.domain.store.command

import org.springframework.data.domain.Pageable

class StoreListCommand(
    val longitude: Double,
    val latitude: Double,
    val category: String?,
    val keyword: String,
    val pageable: Pageable,
)
