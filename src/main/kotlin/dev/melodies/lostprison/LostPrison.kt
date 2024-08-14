package dev.melodies.lostprison

import dev.melodies.commands.admin.AdminCommands
import dev.melodies.commands.player.PlayerCommands
import dev.melodies.enchants.EnchantEffects
import dev.melodies.enchants.PickaxeBreakEvents
import dev.melodies.lostitems.OpenPickaxeMenuListener
import dev.melodies.lostitems.PickaxeGrantListener
import dev.melodies.player.PlayerSkillDataStorage
import dev.melodies.player.PlayerSkills
import dev.melodies.player.SkillMenuListener
import dev.melodies.player.TitleDisplayListener
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import org.incendo.cloud.annotations.AnnotationParser
import org.incendo.cloud.bukkit.CloudBukkitCapabilities
import org.incendo.cloud.execution.ExecutionCoordinator
import org.incendo.cloud.paper.LegacyPaperCommandManager
import java.io.File

@Suppress("unused")
class LostPrison : JavaPlugin() {

    lateinit var dataConfig: YamlConfiguration
    lateinit var dataFile: File

    lateinit var playerSkillDataStorage: PlayerSkillDataStorage

    override fun onEnable() {
        saveDefaultConfig()

        // Create a new config file for player data
        dataFile = File(dataFolder, "player-data.yml")
        if (!dataFile.exists()) {
            dataFile.createNewFile()
        }

        dataConfig = YamlConfiguration.loadConfiguration(dataFile)

        playerSkillDataStorage = PlayerSkillDataStorage(this)
        Bukkit.getPluginManager().registerEvents(playerSkillDataStorage, this)

        Bukkit.getPluginManager().registerEvents(PickaxeGrantListener(), this)
        Bukkit.getPluginManager().registerEvents(OpenPickaxeMenuListener(), this)
        Bukkit.getPluginManager().registerEvents(PickaxeBreakEvents(), this)
        Bukkit.getPluginManager().registerEvents(EnchantEffects(), this)

        Bukkit.getPluginManager().registerEvents(PlayerSkills(this), this)

        Bukkit.getPluginManager().registerEvents(TitleDisplayListener(), this)
        Bukkit.getPluginManager().registerEvents(SkillMenuListener(), this)

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord")

        val commandManager = LegacyPaperCommandManager.createNative(
            this, ExecutionCoordinator.simpleCoordinator()
        )

        if (commandManager.hasCapability(CloudBukkitCapabilities.NATIVE_BRIGADIER)) {
            commandManager.registerBrigadier()
        } else if (commandManager.hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
            commandManager.registerAsynchronousCompletions()
        }

        val annotationParser = AnnotationParser(commandManager, CommandSender::class.java)
        annotationParser.parse(this, PlayerCommands(this), AdminCommands(this))

        logger.info("LostPrison has been enabled!")
    }
}