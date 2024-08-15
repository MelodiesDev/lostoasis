package dev.melodies.player.skills

import dev.melodies.lostprison.LostPrison

class SkillConfig(private val plugin: LostPrison) {

    /**
     * Returns the required XP for a given level
     *
     * @param level The level to lookup.
     * @return The required XP to level up.
     */
    fun getRequiredXP(level: Int): Double =
        plugin.config.getDouble("level-xp.$level", 1000.0)

    fun getProgress(level: Int, xp: Double): Double =
        (xp / getRequiredXP(level)).coerceIn(0.0..1.0)

    fun getProgressColor(currentLevel: Int): String {
        return when {
            currentLevel >= 50 -> "dark_red" // dark red
            currentLevel >= 45 -> "red" // red
            currentLevel >= 40 -> "yellow" // yellow
            currentLevel >= 35 -> "gold" // gold
            currentLevel >= 30 -> "dark_purple" // dark purple
            currentLevel >= 25 -> "light_purple" // light purple
            currentLevel >= 20 -> "aqua" // aqua
            currentLevel >= 15 -> "dark_aqua" // dark aqua
            currentLevel >= 10 -> "blue" // blue
            currentLevel >= 5 -> "green" // green
            else -> "dark_green" // dark green
        } // Todo: come up with something better
    }
}