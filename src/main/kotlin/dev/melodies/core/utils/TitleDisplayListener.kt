package dev.melodies.core.utils

import dev.melodies.lostoasis.LostOasis
import net.kyori.adventure.title.Title
import net.kyori.adventure.util.Ticks
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class TitleDisplayListener(private val plugin: LostOasis) : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        player.showTitle(
            Title.title(
                TitleDisplayManager.joinTitle,
                TitleDisplayManager.joinSubtitle,
                Title.Times.times(
                    Ticks.duration(10),
                    Ticks.duration(90),
                    Ticks.duration(10)
                )
            )
        )
    }
}