package dev.melodies.core.utils

import com.google.common.io.ByteStreams
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin


object PlayerServerUtils {
    fun transfer(plugin: Plugin, player: Player, server: String) {
        val out = ByteStreams.newDataOutput()
        out.writeUTF("Connect")
        out.writeUTF(server)

        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray())
    }

    fun transferToLobby(plugin: Plugin, sender: Player) {
        transfer(plugin, sender, "lobby")
    }
}