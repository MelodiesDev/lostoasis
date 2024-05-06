package dev.melodies.commands

import dev.melodies.utils.PlayerServerUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class ServerCommands(private val plugin: Plugin) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val player = sender as? Player ?: return false
        if (sender.hasPermission("melodies.command.server")) {
            PlayerServerUtils.transfer(plugin, player, "lobby")
            sender.sendMessage("Transferring you now!")
        } else
            sender.sendMessage("You do not have permission to use this command.")
        return false
    }
}