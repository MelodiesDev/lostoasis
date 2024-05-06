package dev.melodies.player

import dev.melodies.utils.toMiniMessage
import net.kyori.adventure.title.Title
import net.kyori.adventure.util.Ticks
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinMOTD : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        player.showTitle(
            Title.title(
                "Welcome to Lost Prison!".toMiniMessage(),
                "<gradient:aqua:dark_purple>The Current Event is Gang Rape</gradient>".toMiniMessage(),
                Title.Times.times(
                    Ticks.duration(10),
                    Ticks.duration(90),
                    Ticks.duration(10)
                )
            )
        )
    }
}