package org.inner.circle.o2oserver.store.infrastructure.repository.mongo
import org.inner.circle.o2oserver.store.domain.store.Store
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "shop")
data class Store(
    @Id
    @Field("_id")
    val id: String,
//    val storeId: Long,
    val storeName: String,
    val zipCode: String?,
    val address: String?,
    val latitude: Double,
    val longitude: Double,
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    val location: GeoJsonPoint,
) {
    // 일단 임시로 임의값 넣음
    fun toDomain(): Store {
        return Store(
            storeId = 1,
            zipCode = zipCode,
            address = address,
            longitude = longitude,
            storeName = storeName,
            openTime = "09:00",
            closeTime = "18:00",
            category = "카테고리",
            reviewCount = 999,
            reviewRate = 4.5,
            thumbnails = listOf(
                "https://www.adobe.com/kr/creativecloud/photography/hub/features/media_19243bf806dc1c5a3532f3e32f4c14d44f81cae9f.jpeg?width=750&format=jpeg&optimize=medium",
            ),
            menus = listOf(),
        )
    }
}
