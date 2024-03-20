package dev.melodies.lostmenu

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper
import xyz.xenondevs.invui.item.builder.ItemBuilder

class CompassGrantListener : Listener {
    companion object {
        val KEY = NamespacedKey("lost-items", "compass")

        val COMPASS: ItemStack = ItemBuilder(Material.COMPASS)
            .setDisplayName("<gradient:aqua:dark_purple>Navigator</gradient>".toMiniMessage().wrapped())
            .addLoreLines("<dark_purple>A dimensional Navigator.</dark_purple>".toMiniMessage().wrapped())
            .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
            .get()
            .also { item ->
                item.editMeta {
                    it.persistentDataContainer.set(KEY, PersistentDataType.BOOLEAN, true)
                }
            }
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        val inv = player.inventory

        inv.setItem(8, (COMPASS))
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.currentItem?.isSimilar(COMPASS) == true
        ) {
            event.isCancelled = true
        }
    }
}



private fun String.toMiniMessage() = MiniMessage.miniMessage().deserialize(this)

private fun Component.wrapped() = AdventureComponentWrapper(this)