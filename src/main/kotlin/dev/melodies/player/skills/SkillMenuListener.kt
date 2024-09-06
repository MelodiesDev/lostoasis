package dev.melodies.player.skills

import dev.melodies.utils.InventoryManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class SkillMenuListener : Listener {
    @EventHandler
    fun cancelClick(event: InventoryClickEvent) {
        if (event.view.title() != InventoryManager.skillMenuTitle
            && event.view.title() != InventoryManager.miningMenuTitle
            && event.view.title() != InventoryManager.forestryMenuTitle
        ) return

        event.isCancelled = true
    }
}