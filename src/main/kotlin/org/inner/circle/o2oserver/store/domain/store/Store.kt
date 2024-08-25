package org.inner.circle.o2oserver.store.domain.store

class Store(
    val storeId: Long,
    val storeName: String,
    val contactNumber: String? = null,
    val zipCode: String? = null,
    val address: String? = null,
    val addressDetail: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val openTime: String? = null,
    val closeTime: String? = null,
    val category: String? = null,
    val deliveryArea: String? = null,
    val minimumPrice: Int? = null,
    val reviewCount: Int? = null,
    val reviewRate: Double? = null,
    val thumbnails: List<String>? = null,
)
