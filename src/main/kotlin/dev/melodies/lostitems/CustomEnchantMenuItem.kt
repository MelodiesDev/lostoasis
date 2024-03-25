package dev.melodies.lostitems

import dev.melodies.enchants.CustomEnchantment
import dev.melodies.enchants.getCustomEnchantLevel
import dev.melodies.enchants.setCustomEnchantLevel
import dev.melodies.utils.toMiniMessage
import dev.melodies.utils.wrapped
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.AbstractItem
import kotlin.math.max
import kotlin.math.roundToInt


class CustomEnchantMenuItem(
    private val enchantment: CustomEnchantment,
    private val pickaxe: ItemStack
) : AbstractItem() {

    override fun handleClick(clickType: ClickType, player: Player, event: InventoryClickEvent) {
        var count = pickaxe.getCustomEnchantLevel(enchantment) ?: 0

        if (clickType.isLeftClick) {
            if (clickType.isShiftClick) count += 64 // if shift click, add 64
            else count++
            player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
        } else {
            count-- // else decrement
        }

        if (count < 0) count = 0 // don't allow negative values
        pickaxe.setCustomEnchantLevel(enchantment, count) // set the new level

        notifyWindows() // this will update the ItemStack that is displayed to the player
    }

    override fun getItemProvider(): ItemProvider {
        val level = pickaxe.getCustomEnchantLevel(enchantment) ?: 0
        return ItemBuilder(enchantment.material)
            .setDisplayName(enchantment.name.toMiniMessage().wrapped())
            .addLoreLines(
                "".toMiniMessage().wrapped(),
                enchantment.description.toMiniMessage().wrapped(),
                "".toMiniMessage().wrapped(),
                "<gray>Left Click <green>+1</green> Right Click <red>-1</red></gray>".toMiniMessage().wrapped(),
                "".toMiniMessage().wrapped(),
                "<blue>Current proc chance:</blue> <green>${(level * enchantment.procPerLevel * 100).roundToInt()}%</green>".toMiniMessage().wrapped(),
            )
            .setAmount(max(level, 1))
    }

}