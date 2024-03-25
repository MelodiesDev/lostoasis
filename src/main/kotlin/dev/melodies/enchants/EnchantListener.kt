package dev.melodies.enchants

import dev.melodies.utils.toMiniMessage
import org.bukkit.Material
import org.bukkit.entity.FallingBlock
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityChangeBlockEvent
import java.util.*

class PickaxeEnchantListener : Listener {

    private val meteors = mutableSetOf<UUID>()

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
        event.player.sendMessage("<green>Fortune Triggered!</green>".toMiniMessage())
    }

    @EventHandler
    fun explosiveEffect(event: BlockBreakEvent) {
        val item = event.player.inventory.itemInMainHand
        val level = item.getCustomEnchantLevel(CustomEnchantments.EXPLOSIVE) ?: return
        val chance = level * CustomEnchantments.EXPLOSIVE.procPerLevel

        // TODO: Move proc chance to a function
        if (Math.random() > chance) return

        event.block.world.createExplosion(event.block.location, 2.0f, false)
        event.player.sendMessage("<green>Explosion Triggered!</green>".toMiniMessage())
    }

    @EventHandler
    fun meteorRainEffectSpawn(event: BlockBreakEvent) {
        val item = event.player.inventory.itemInMainHand
        val level = item.getCustomEnchantLevel(CustomEnchantments.METEOR) ?: return
        val chance = level * CustomEnchantments.METEOR.procPerLevel
        if (Math.random() > chance) return

        for (i in 0..level) {
            val random = java.util.Random()
            val xOffset = random.nextInt(10) - 5
            val zOffset = random.nextInt(10) - 5
            val location = event.block.location.add(xOffset.toDouble(), 10.0, zOffset.toDouble())

            val entity = location.world.spawn(location, FallingBlock::class.java) {
                it.blockData = Material.GRAVEL.createBlockData()
                it.dropItem = false
            }

            meteors.add(entity.uniqueId)
        }

        event.player.sendMessage("<green>Meteor Rain Triggered!</green>".toMiniMessage())
    }

    @EventHandler
    fun meteorRainEffectFall(event: EntityChangeBlockEvent) {
        val entity = event.entity as? FallingBlock ?: return
        val uuid = entity.uniqueId
        if (uuid !in meteors) return

        meteors.remove(uuid)
        event.isCancelled = true

        entity.world.createExplosion(entity.location, 2.0f, false)
    }
}