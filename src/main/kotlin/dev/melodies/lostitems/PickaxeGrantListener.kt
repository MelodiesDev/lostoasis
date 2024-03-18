package dev.melodies.lostitems

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper
import xyz.xenondevs.invui.item.builder.ItemBuilder

class PickaxeGrantListener : Listener {
    companion object {
        val KEY = NamespacedKey("lost-items", "pickaxe")

        val PICKAXE: ItemStack = ItemBuilder(Material.WOODEN_PICKAXE)
            .setDisplayName("<#964B00>Wooden Pickaxe</#964B00>".toMiniMessage().wrapped())
            .addLoreLines("<gray>A shoddy Wooden Pickaxe.</gray>".toMiniMessage().wrapped())
            .setUnbreakable(true)
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
        player.inventory.setItem(0, PICKAXE)
        checkPickaxe(player)
    }

    private fun checkPickaxe(player: Player) {
        val inv = player.inventory
        val firstSlotItem = inv.getItem(0)
        if (firstSlotItem?.isSimilar(PICKAXE) != true) {
            inv.setItem(0, PICKAXE.clone())
        }
    }

    @EventHandler
    fun inventoryClick(event: InventoryClickEvent) {
        if (event.currentItem?.isSimilar(PICKAXE) == true) {
            event.isCancelled = true
        }
    }
}

private fun String.toMiniMessage() = MiniMessage.miniMessage().deserialize(this)

private fun Component.wrapped() = AdventureComponentWrapper(this)