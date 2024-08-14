package dev.melodies.player

import dev.melodies.utils.toMiniMessage
import net.kyori.adventure.title.Title
import net.kyori.adventure.util.Ticks
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class TitleDisplayListener : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        player.showTitle(
            Title.title(
                joinTitle,
                joinSubtitle,
                Title.Times.times(
                    Ticks.duration(10),
                    Ticks.duration(90),
                    Ticks.duration(10)
                )
            )
        )
    }

    companion object {
        val joinTitle = "Welcome to Lost Prison!".toMiniMessage()
        val joinSubtitle = "<gradient:aqua:dark_purple>hi :3</gradient>".toMiniMessage()

        val mineTitle = "Mining".toMiniMessage()
        val mineSubtitle = "Mining is fun!".toMiniMessage()
    }
}