package dev.melodies.utils

import dev.melodies.lostprison.LostPrison
import dev.melodies.player.skills.SkillType
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

object InventoryManger {
    val skillMenuTitle = "Skills".toMiniMessage()
    val miningMenuTitle = "Mining".toMiniMessage()
    val forestryMenuTitle = "Forestry".toMiniMessage()

    fun openSkillMenu(player: Player, plugin: LostPrison) {
        val inventory = Bukkit.createInventory(null, InventoryType.CHEST, skillMenuTitle)

        val miningData = plugin.playerSkillDataStorage.getSkillData(player.uniqueId, SkillType.MINING)
        val forestryData = plugin.playerSkillDataStorage.getSkillData(player.uniqueId, SkillType.FORESTRY)

        val neededXP = plugin.skillConfig.getRequiredXP(miningData.level) + plugin.skillConfig.getRequiredXP(forestryData.level)
        val progress = plugin.skillConfig.getProgress(miningData.level, miningData.xp) + plugin.skillConfig.getProgress(forestryData.level, forestryData.xp)
        val progressBar = ProgressBarBuilder.buildProgressBar(progress, 30)

        val mining = ItemStack(Material.DIAMOND_PICKAXE)
        mining.editMeta { meta ->
            meta.displayName("<gray><b>MINING".toMiniMessage())
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
            meta.lore(
                mutableListOf(
                    "<green>${miningData.level}</green> - <white>${(progress * 100.0).toInt()}</white>%",
                    "<dark_gray>[</dark_gray>$progressBar<dark_gray>]</dark_gray>",
                    "<gray><b>XP:</gray> <white>${miningData.xp}</white>/<white>$neededXP</white>"
                ).map { it.toMiniMessage() })
        }

        val forestry = ItemStack(Material.DIAMOND_AXE)
        forestry.editMeta { meta ->
            meta.displayName("<dark_green><b>FORESTRY".toMiniMessage())
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
            meta.lore(
                mutableListOf(
                    "<green>${forestryData.level}</green> - <white>${(progress * 100.0).toInt()}</white>%",
                    "<dark_gray>[</dark_gray>$progressBar<dark_gray>]</dark_gray>",
                    "<gray><b>XP:</gray> <white>${forestryData.xp}</white>/<white>$neededXP</white>"
                ).map { it.toMiniMessage() })
        }
        inventory.setItem(11, mining)
        inventory.setItem(13, forestry)

        player.openInventory(inventory)
    }

    fun openMiningMenu(player: Player, plugin: LostPrison) {
        val inventory = Bukkit.createInventory(null, InventoryType.CHEST, miningMenuTitle)
        val items = InventoryContents.miningMenuContents(plugin, player)

        inventory.setItem(13, items[0])
        inventory.setItem(11, items[1])
        inventory.setItem(15, items[2])

        player.openInventory(inventory)
    }
}