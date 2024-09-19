package org.inner.circle.o2oserver.store.domain

class MenuOptionGroup(
    val menuOptionGroupId: Long?,
    val menuOptionGroupName: String?,
    val menuOptions: List<MenuOption>?,
    val isRequired: Boolean?,
    val isMultiple: Boolean?,
)
