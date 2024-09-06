package dev.melodies.core.utils.commands

import dev.melodies.utils.InventoryManager
import dev.melodies.lostoasis.LostOasis
import org.bukkit.entity.Player
import org.incendo.cloud.annotations.Command

class SkillCommands(private val plugin: LostOasis) {

    @Command("skills")
    fun skill(sender: Player) {
        InventoryManager.openSkillMenu(sender, plugin)
    }
}