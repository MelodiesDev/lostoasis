package dev.melodies.lostitems

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class PickaxeEnchantListener : Listener {

    @EventHandler
    fun fortuneEffect(event: BlockBreakEvent) {
        val item = event.player.inventory.itemInMainHand
        val level = item.getCustomEnchantLevel(CustomEnchantments.FORTUNE) ?: return
        val chance = level * CustomEnchantments.FORTUNE.procPerLevel

        // TODO: Move proc chance to a function
        if (Math.random() > chance) return

        val allDrops = event.block.getDrops(item)
        allDrops.forEach {
            event.block.world.dropItemNaturally(event.block.location, it)
        }
        event.player.sendMessage("Fortune proc!")
    }

    @EventHandler
    fun explosiveEffect(event: BlockBreakEvent) {
        val item = event.player.inventory.itemInMainHand
        val level = item.getCustomEnchantLevel(CustomEnchantments.EXPLOSIVE) ?: return
        val chance = level * CustomEnchantments.EXPLOSIVE.procPerLevel

        // TODO: Move proc chance to a function
        if (Math.random() > chance) return

        event.block.world.createExplosion(event.block.location, 2.0f, false)
        event.player.sendMessage("Explosive proc!")
    }

}