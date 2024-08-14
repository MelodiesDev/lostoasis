package dev.melodies.player

import dev.melodies.utils.toMiniMessage
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack

object SkillMenuManager {
    val title = "Skills".toMiniMessage()

    fun openSkillMenu(player: Player) {
        val inventory = Bukkit.createInventory(null, InventoryType.CHEST, title)
        val mining = ItemStack(Material.DIAMOND_PICKAXE)
        inventory.setItem(0, mining)
        player.openInventory(inventory)
    }
}