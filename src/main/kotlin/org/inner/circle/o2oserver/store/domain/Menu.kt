package org.inner.circle.o2oserver.store.domain

class Menu(
    val menuId: Long?,
    val menuName: String,
    val menuPrice: Long?,
    val menuCount: Int?,
    val menuOptionGroups: List<MenuOptionGroup>?,
    val description: String?,
)
