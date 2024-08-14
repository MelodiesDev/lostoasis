package dev.melodies.player

import dev.melodies.lostprison.LostPrison
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import java.util.UUID

class PlayerSkillDataStorage(private val plugin: LostPrison) : Listener {
    private val cache = mutableMapOf<UUID, PlayerSkillData>()

    fun getMiningLevelXP(playerID: UUID): PlayerSkillData = cache.getOrPut(playerID) {
        val level = plugin.dataConfig.getInt("player-data.$playerID.mining-level", 1)
        val xp = plugin.dataConfig.getDouble("player-data.$playerID.mining-xp", 0.0)
        PlayerSkillData(level, xp)
    }

    fun setPlayerStats(playerId: UUID, level: Int, xp: Double) {
        cache[playerId] = PlayerSkillData(level, xp)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val data = cache.remove(event.player.uniqueId) ?: return

        plugin.dataConfig.set("player-data.${event.player.uniqueId}.mining-level", data.level)
        plugin.dataConfig.set("player-data.${event.player.uniqueId}.mining-xp", data.xp)
        plugin.dataConfig.save(plugin.dataFile)
    }
}

data class PlayerSkillData(val level: Int, val xp: Double)