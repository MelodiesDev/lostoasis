package dev.melodies.commands.player

import dev.melodies.lostprison.LostPrison
import dev.melodies.utils.InventoryManger
import dev.melodies.utils.PlayerServerUtils
import dev.melodies.utils.PluginBooks
import dev.melodies.utils.toMiniMessage
import org.bukkit.entity.Player
import org.incendo.cloud.annotations.Command

class PlayerCommands(private val plugin: LostPrison) {
    @Command("skills")
    fun skill(sender: Player) {
        InventoryManger.openSkillMenu(sender, plugin)
    }

    @Command("info")
    fun info(sender: Player) {
        PluginBooks.openInfoBook(sender)
    }

    @Command("hub")
    fun hub(sender: Player) {
        PlayerServerUtils.transferToLobby(plugin, sender)
        sender.sendMessage("Transferring you now!".toMiniMessage())
    }
}