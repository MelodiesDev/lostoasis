package dev.melodies.player

import dev.melodies.lostprison.LostPrison
import dev.melodies.utils.toMiniMessage
import net.kyori.adventure.text.format.TextColor
import org.bukkit.entity.Player
import kotlin.math.roundToInt

class ActionBarManager(private val plugin: LostPrison) {

    fun displayMiningBar(player: Player, currentXP: Double, requiredXP: Double, xp: Double) {
        val data = plugin.playerSkillDataStorage.getMiningLevelXP(player.uniqueId)
        val currentLevel = data.level
        // Ensure requiredXP is positive to avoid division by zero
        if (requiredXP <= 0) return

        val progress = (currentXP / requiredXP).coerceIn(0.0..1.0) // Ensure progress is between 0 and 1
        val progressBar = buildProgressBar(progress, 30)
        val progressPercentage = (progress * 100).toInt()
        val progressColor = getProgressColor(progressPercentage)
        val message = "<dark_gray>[</dark_gray><green>$currentLevel</green><dark_gray>]</dark_gray> <gray><b>MINING:</gray> <dark_gray>[</dark_gray>$progressBar<dark_gray>]</dark_gray> <$progressColor>${progressPercentage}%</$progressColor> <dark_gray>(</dark_gray>+<green>$xp</green><dark_gray>)</dark_gray>"
        player.sendActionBar(message.toMiniMessage())
    }

    private fun buildProgressBar(progress: Double, width: Int): String {
        val completedBars = (progress * width).toInt()
        val remainingBars = width - completedBars
        return "<green>|</green>".repeat(completedBars) + "<gray>|</gray>".repeat(remainingBars)
    }

    private fun getProgressColor(progressPercentage: Int): String {
        return when {
            progressPercentage >= 95 -> "gradient:#e81416:#ffa500:#faeb36:#79c314:#487de7:#4b369d:#70369d" // Placeholder for fancily formatting the color
            progressPercentage >= 80 -> "green" // Green
            progressPercentage >= 60 -> "gold" // Yellow
            progressPercentage >= 40 -> "#ffa500" // Orange
            progressPercentage >= 20 -> "#FF5555" // Darker red
            else -> "#AA0000" // Dark red
        } // Todo: come up with something better
    }
}