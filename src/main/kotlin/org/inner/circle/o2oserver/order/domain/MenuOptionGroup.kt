package org.inner.circle.o2oserver.order.domain

class MenuOptionGroup(
    val menuOptionGroupId: Long,
    val name: String? = "",
    val required: Boolean? = false,
    val menuOptions: List<MenuOption>,
) {
}
