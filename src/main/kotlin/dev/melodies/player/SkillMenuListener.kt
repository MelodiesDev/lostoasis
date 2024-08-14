package dev.melodies.player

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class SkillMenuListener : Listener {
    @EventHandler
    fun cancelClick(event: InventoryClickEvent) {
        if (event.view.title() != SkillMenuManager.title) return
        println("Cancelled")
        event.isCancelled = true
    }
}