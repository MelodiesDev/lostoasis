package dev.melodies.commands

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BookMeta

class OpenInfoBookCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        val player = sender as? Player ?: return false
        if (sender.hasPermission("melodies.command.infobook")) {
            val book = ItemStack(Material.WRITTEN_BOOK)
            book.editMeta(BookMeta::class.java) { meta ->
                meta.title(MiniMessage.miniMessage().deserialize("<gradient:blue:aqua>Info</gradient>"))
                meta.author(MiniMessage.miniMessage().deserialize("<gradient:blue:aqua>Server</gradient>"))
                meta.addPages(
                    MiniMessage.miniMessage().deserialize(
                        """
                        <b><dark_purple>Enchants Vol 1</dark_purple></b>
                        
                        <gradient:blue:aqua>Efficiency</gradient> - Increases mining speed exponentially increasing with level.
                        
                        <gradient:blue:aqua>Fortune</gradient> - Increases block drops multiplier raises with each level.

                        """.trimIndent()
                    ),
                    MiniMessage.miniMessage().deserialize(
                        """
                        <b><dark_purple>Enchants Vol 2</dark_purple></b>
                        
                        <gradient:blue:aqua>Explosive</gradient> - Chance to explode blocks in a larger radius each level.
                        
                        <gradient:blue:aqua>Meteor Rain</gradient> - Summons meteors to rain down increasing in quantity each level.

                        """.trimIndent()
                    ),
                    MiniMessage.miniMessage().deserialize(
                        """
                        <b><dark_purple>Enchants Vol 3</dark_purple></b>

                        <gradient:blue:aqua>Lightning Storm</gradient> - Summons a lightning storm that breaks blocks randomly.

                        """.trimIndent()
                    )
                )
            }
            player.openBook(book)
        } else
            sender.sendMessage("You do not have permission to use this command.")
        return false
    }
}