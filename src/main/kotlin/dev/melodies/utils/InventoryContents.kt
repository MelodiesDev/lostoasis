package dev.melodies.utils

import dev.melodies.core.utils.toMiniMessage
import dev.melodies.lostoasis.LostOasis
import dev.melodies.player.skills.SkillType
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

object InventoryContents {
    fun miningMenuContents(plugin: LostOasis, player: Player): List<ItemStack> {
        val miningData = plugin.playerSkillDataStorage.getSkillData(player.uniqueId, SkillType.MINING)
        val neededXP = plugin.skillConfig.getRequiredXP(miningData.level)
        val progress = plugin.skillConfig.getProgress(miningData.level, miningData.xp)
        val progressBar = ProgressBarBuilder.buildProgressBar(progress, 30)

        val tokens = 500

        val centerItem = ItemStack(Material.ENCHANTING_TABLE)
        centerItem.editMeta { meta ->
            meta.displayName("<gray><b>MINING".toMiniMessage())
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
            meta.lore(
                mutableListOf(
                    "<green>${miningData.level}</green> - <white>${(progress * 100.0).toInt()}</white>%",
                    progressBar,
                    "$neededXP"
                ).map { it.toMiniMessage() })
        }

        val skills = ItemStack(Material.NETHERITE_PICKAXE)
        skills.editMeta { meta ->
            meta.displayName("<yellow><b>SKILLS".toMiniMessage())
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
            meta.lore(
                mutableListOf(
                    "<gray><b>Tokens:</gray> <gold>$tokens</gold>"
                ).map { it.toMiniMessage() })
        }

        val enchants = ItemStack(Material.NETHER_STAR)
        enchants.editMeta { meta ->
            meta.displayName("<dark_purple><b>ENCHANTS".toMiniMessage())
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
            meta.lore(
                mutableListOf(
                    "<gray>Enchant your pickaxe to gain great power!</gray>"
                ).map { it.toMiniMessage() })
        }

        return listOf(centerItem, skills, enchants)
    }
}