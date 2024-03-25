package dev.melodies.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper

fun String.toMiniMessage() = MiniMessage.miniMessage().deserialize(this)

fun Component.wrapped() = AdventureComponentWrapper(this)