package dev.melodies.lostoasis

import dev.melodies.core.utils.ScoreboardManager
import dev.melodies.core.utils.TitleDisplayListener
import dev.melodies.core.utils.commands.SkillCommands
import dev.melodies.player.skills.PlayerSkillDataStorage
import dev.melodies.player.skills.PlayerSkills
import dev.melodies.player.skills.SkillConfig
import dev.melodies.player.skills.SkillMenuListener
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
class LostOasis : JavaPlugin() {

    lateinit var dataConfig: YamlConfiguration
    lateinit var dataFile: File

    lateinit var playerSkillDataStorage: PlayerSkillDataStorage
    lateinit var skillConfig: SkillConfig

    override fun onEnable() {
        saveDefaultConfig()

        // Create a new config file for player data
        dataFile = File(dataFolder, "player-data.yml")
        if (!dataFile.exists()) {
            dataFile.createNewFile()
        }

        dataConfig = YamlConfiguration.loadConfiguration(dataFile)

        playerSkillDataStorage = PlayerSkillDataStorage(this)
        skillConfig = SkillConfig(this)

        Bukkit.getPluginManager().registerEvents(playerSkillDataStorage, this)

        Bukkit.getPluginManager().registerEvents(PlayerSkills(this), this)

        Bukkit.getPluginManager().registerEvents(TitleDisplayListener(this), this)
        Bukkit.getPluginManager().registerEvents(ScoreboardManager(), this)

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
        annotationParser.parse(this, SkillCommands(this))


        logger.info("LostPrison has been enabled!")
    }
}