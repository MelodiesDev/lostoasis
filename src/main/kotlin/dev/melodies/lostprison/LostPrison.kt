package dev.melodies.lostprison

import dev.melodies.commands.OpenInfoBookCommand
import dev.melodies.commands.ServerCommands
import dev.melodies.enchants.EnchantEffects
import dev.melodies.enchants.PickaxeBreakEvents
import dev.melodies.lostitems.OpenPickaxeMenuListener
import dev.melodies.lostitems.PickaxeGrantListener
import dev.melodies.player.PlayerJoinMOTD
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class LostPrison : JavaPlugin(){
    override fun onEnable() {
        logger.info("LostPrison has been enabled!")
        Bukkit.getPluginManager().registerEvents(PickaxeGrantListener(), this)
        Bukkit.getPluginManager().registerEvents(OpenPickaxeMenuListener(), this)
        Bukkit.getPluginManager().registerEvents(PickaxeBreakEvents(), this)
        Bukkit.getPluginManager().registerEvents(EnchantEffects(), this)
        Bukkit.getPluginManager().registerEvents(PlayerJoinMOTD(), this)
        this.getCommand("info")?.setExecutor(OpenInfoBookCommand())
        this.getCommand("hub")?.setExecutor(ServerCommands(this))

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord")
    }
}