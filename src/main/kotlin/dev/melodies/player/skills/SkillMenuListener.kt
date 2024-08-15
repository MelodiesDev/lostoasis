package dev.melodies.player.skills

import dev.melodies.lostprison.LostPrison
import dev.melodies.utils.InventoryManger
import dev.melodies.utils.toMiniMessage
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class SkillMenuListener(private val plugin: LostPrison) : Listener {
    @EventHandler
    fun cancelClick(event: InventoryClickEvent) {
        if (event.view.title() != InventoryManger.skillMenuTitle
            && event.view.title() != InventoryManger.miningMenuTitle
            && event.view.title() != InventoryManger.forestryMenuTitle
        ) return

        event.isCancelled = true
    }

    @EventHandler
    fun selectMining(event: InventoryClickEvent) {
        val player = event.whoClicked as Player
        if (event.view.title() != InventoryManger.skillMenuTitle) return
        if (event.currentItem?.itemMeta?.displayName("<gray><b>MINING".toMiniMessage()) == null) return

        InventoryManger.openMiningMenu(player, plugin)
        player.playSound(player.location, Sound.BLOCK_WOOD_HIT, 1f, 1f)
    }
}