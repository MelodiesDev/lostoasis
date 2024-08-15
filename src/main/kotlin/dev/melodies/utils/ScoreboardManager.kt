package dev.melodies.utils

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.RenderType

class ScoreboardManager : Listener{
    @EventHandler
    fun displayScoreboard(event: PlayerJoinEvent) {
        val player = event.player
        val scoreboard = event.player.scoreboard.registerNewObjective("Scoreboard", Criteria.DUMMY, "LostOasis".toMiniMessage(), RenderType.INTEGER)
        scoreboard.displaySlot = DisplaySlot.SIDEBAR
    }
}