package dev.melodies.player.skills

import dev.melodies.lostoasis.LostOasis
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*

class PlayerSkillDataStorage(private val plugin: LostOasis) : Listener {
    private val cache = mutableMapOf<UUID, PlayerSkillsData>()

    fun getSkillData(playerID: UUID, type: SkillType): PlayerSkillData = cache.getOrPut(playerID) {
        val levels = SkillType.entries.associateWith { type ->
            val level = plugin.dataConfig.getInt("player-data.$playerID.$type.level", 1)
            val xp = plugin.dataConfig.getDouble("player-data.$playerID.$type.xp", 0.0)
            PlayerSkillData(level, xp)
        }

        PlayerSkillsData(levels)
    }.levels[type] ?: PlayerSkillData()

    fun setPlayerStats(playerId: UUID, type: SkillType, level: Int, xp: Double) {
        val data = cache.getOrPut(playerId) { PlayerSkillsData() }
        val newData = data.copy(levels = data.levels + mapOf(type to PlayerSkillData(level, xp)))
        cache[playerId] = newData
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val data = cache.remove(event.player.uniqueId) ?: return

        data.levels.forEach { (type, skillData) ->
            plugin.dataConfig.set("player-data.${event.player.uniqueId}.$type.level", skillData.level)
            plugin.dataConfig.set("player-data.${event.player.uniqueId}.$type.xp", skillData.xp)
        }

        plugin.dataConfig.save(plugin.dataFile)
    }
}

data class PlayerSkillsData(
    val levels: Map<SkillType, PlayerSkillData> = emptyMap(),
)

data class PlayerSkillData(
    val level: Int = 1,
    val xp: Double = 0.0
)

enum class SkillType {
    MINING, FORESTRY
}