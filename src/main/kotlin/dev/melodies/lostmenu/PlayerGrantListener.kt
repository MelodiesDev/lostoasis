package dev.melodies.lostmenu

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack

class PlayerGrantListener : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player

        val compass = ItemStack(Material.COMPASS)
        compass.editMeta {
            it.displayName(MiniMessage.miniMessage().deserialize("<gradient:aqua:blue>Navigator</gradient>"))
        }

        player.inventory.setItem(8, compass)
        if (player.inventory.getItem(8) != compass) {
            player.inventory.setItem(8, compass)
        }
    }
}