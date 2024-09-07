package org.inner.circle.o2oserver.store.domain.menu

import org.inner.circle.o2oserver.store.domain.menu.optiongroup.MenuOptionGroup

class Menu(
    val menuId: Int?,
    val menuName: String,
    val description: String?,
    val menuPrice: Int?,
    val optionGroups: List<MenuOptionGroup>?,
)
