package dev.melodies.player

import dev.melodies.lostprison.LostPrison
import dev.melodies.player.skills.SkillType
import dev.melodies.utils.ProgressBarBuilder
import dev.melodies.utils.toMiniMessage
import org.bukkit.entity.Player

class ActionBarManager(private val plugin: LostPrison) {

    fun displaySkillBar(player: Player, currentXP: Double, requiredXP: Double, xp: Double) {
        val data = plugin.playerSkillDataStorage.getSkillData(player.uniqueId, SkillType.MINING)
        val currentLevel = data.level
        val currentSkill = SkillType.MINING.name
        // Ensure requiredXP is positive to avoid division by zero
        if (requiredXP <= 0) return

        val progress = (currentXP / requiredXP).coerceIn(0.0..1.0) // Ensure progress is between 0 and 1
        val progressBar = ProgressBarBuilder.buildProgressBar(progress, 30)
        val progressPercentage = (progress * 100).toInt()
        val progressColor = plugin.skillConfig.getProgressColor(currentLevel)

        val message = "<dark_gray>[</dark_gray><$progressColor>$currentLevel</$progressColor><dark_gray>]</dark_gray> <gray><b>$currentSkill</gray> <dark_gray>[</dark_gray>$progressBar<dark_gray>]</dark_gray> ${progressPercentage}% <dark_gray>(</dark_gray>+<green>$xp</green><dark_gray>)</dark_gray>"
        player.sendActionBar(message.toMiniMessage())
    }
}