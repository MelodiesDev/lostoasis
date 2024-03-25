package dev.melodies.lostplugins

import dev.melodies.commands.OpenInfoBookCommand
import dev.melodies.losthub.PlayerDoubleJump
import dev.melodies.lostitems.OpenPickaxeMenuListener
import dev.melodies.lostitems.PickaxeBreakEvents
import dev.melodies.lostitems.PickaxeEnchantListener
import dev.melodies.lostitems.PickaxeGrantListener
import dev.melodies.lostmenu.CompassGrantListener
import dev.melodies.lostmenu.OpenNavigatorListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class LostPlugins : JavaPlugin(){
    override fun onEnable() {
        logger.info("LostPlugins has been enabled!")
        Bukkit.getPluginManager().registerEvents(PickaxeGrantListener(), this)
        Bukkit.getPluginManager().registerEvents(OpenPickaxeMenuListener(), this)
        Bukkit.getPluginManager().registerEvents(PickaxeBreakEvents(), this)
        Bukkit.getPluginManager().registerEvents(PickaxeEnchantListener(), this)
        Bukkit.getPluginManager().registerEvents(CompassGrantListener(), this)
        Bukkit.getPluginManager().registerEvents(OpenNavigatorListener(), this)
        Bukkit.getPluginManager().registerEvents(PlayerDoubleJump(this), this)
        this.getCommand("info")?.setExecutor(OpenInfoBookCommand())
    }
}