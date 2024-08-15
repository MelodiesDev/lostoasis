package dev.melodies.utils

import dev.melodies.lostprison.LostPrison
import dev.melodies.utils.TitleDisplayManager.joinSubtitle
import dev.melodies.utils.TitleDisplayManager.joinTitle
import net.kyori.adventure.title.Title
import net.kyori.adventure.util.Ticks
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class TitleDisplayListener(private val plugin: LostPrison) : Listener {
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
}