package dev.melodies.player.skills

import dev.melodies.core.utils.TitleDisplayManager
import dev.melodies.lostoasis.LostOasis
import dev.melodies.player.ActionBarManager
import org.bukkit.Material
import org.bukkit.Tag
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack

class PlayerSkills(private val plugin: LostOasis) : Listener {

    private val blocks: Map<Material, Double> = plugin.config.getConfigurationSection("block-xp")?.let { section ->
        section.getKeys(false).mapNotNull { key ->
            val material = Material.getMaterial(key) ?: return@mapNotNull null
            val xp = section.getDouble(key)
            material to xp
        }.toMap()
    } ?: emptyMap()

    private val pickaxes: Map<Material, Double> = plugin.config.getConfigurationSection("pickaxe-multiplier")?.let { section ->
        section.getKeys(false).mapNotNull { key ->
            val item = Material.getMaterial(key) ?: return@mapNotNull null
            val multiplier = section.getDouble(key)
            item to multiplier
        }.toMap()
    } ?: emptyMap()

    private val actionBarManager = ActionBarManager(plugin)

    @EventHandler
    private fun miningSkill(event: BlockBreakEvent) {
        val pickaxeMultiplier = pickaxes[event.player.inventory.itemInMainHand.type] ?: return
        if (!Tag.MINEABLE_PICKAXE.isTagged(event.block.type)) return

        val xp = (blocks[event.block.type] ?: 1.0) * pickaxeMultiplier
        val data = plugin.playerSkillDataStorage.getSkillData(event.player.uniqueId, SkillType.MINING)

        var newXP = data.xp + xp
        var newLevel = data.level

        val xpToLevelUp = plugin.config.getDouble("level-xp.${data.level}", 1000.0)
        if (newXP >= xpToLevelUp) {
            newLevel++
            newXP -= xpToLevelUp

            TitleDisplayManager.handleLevelUp(event.player, data.level, newLevel)
        }

        plugin.playerSkillDataStorage.setPlayerStats(event.player.uniqueId, SkillType.MINING, newLevel, newXP)
        actionBarManager.displaySkillBar(event.player, newXP, xpToLevelUp, xp)
    }
}