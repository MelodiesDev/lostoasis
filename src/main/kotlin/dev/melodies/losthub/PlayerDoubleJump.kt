package dev.melodies.losthub

import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.block.BlockFace
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerToggleSneakEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask
import kotlin.math.min

/**
 * We want to let the player charge their jump, and when they release shift, launch into the air.
 * The launch should scale off the charge time.
 *
 * We can listen to the player sneaking with the [org.bukkit.event.player.PlayerToggleSneakEvent], and
 * then launch a new repeating task to check how long the player is sneaking for.
 *
 * This task will execute every tick, and if the player is still holding down shift, increment the
 * "chargeTime" variable by one.
 *
 * Once the player has stopped holding shift, use the [BukkitTask] instance to cancel the task,
 * which will stop it from repeating. Then, apply velocity to the player by multiplying the "chargeTime" variable
 * by a scaling factor. e.g. chargeTime * 0.05 to multiply by 1 every second the charge time is held.
 * You should probably put a cap on this too.
 */
class PlayerDoubleJump(private val plugin: JavaPlugin) : Listener {

    @EventHandler
    fun onPlayerJump(event: PlayerToggleSneakEvent) {
        // We only care if they just started sneaking
        if (!event.isSneaking) return

        val player = event.player
        val blockBelow = player.location.block.getRelative(BlockFace.DOWN)
        if (!blockBelow.isSolid) return

        var chargeTime = 1
        val chargeDelay = 10L

        Bukkit.getScheduler().runTaskTimer(plugin, { task ->
            // This runs every tick
            if (player.isSneaking) { // If the player is holding shift
                // Increment
                chargeTime++
                if (chargeTime <= chargeDelay) return@runTaskTimer

                // Play a cool ass funking sound.jpeg
                val pitch = min(chargeTime * 0.1f, 2f)
                player.playSound(player, Sound.BLOCK_SCULK_CHARGE, 1f, pitch)
            } else {
                // They stopped
                task.cancel()
                if (chargeTime <= chargeDelay) return@runTaskTimer

                val maxPower = 3.5
                val power = min((chargeTime.toDouble() - chargeDelay) * 0.2, maxPower)

                player.velocity = player.location.direction.multiply(min(power, maxPower))

                player.playSound(player, Sound.ITEM_TRIDENT_RIPTIDE_1, 0.5f, 2f)
                player.spawnParticle(org.bukkit.Particle.BLOCK_CRACK, player.location, 100, 1.0, 0.0, 1.0, 0.0, blockBelow.blockData)

                Bukkit.getScheduler().runTaskTimer(plugin, { task2 ->
                    if (player.location.block.getRelative(BlockFace.DOWN).isEmpty) {
                        player.spawnParticle(org.bukkit.Particle.ELECTRIC_SPARK, player.location, 10, 0.25, 0.5, 0.25, 0.0)
                        player.spawnParticle(org.bukkit.Particle.DRAGON_BREATH, player.location, 10, 0.0, 0.25, 0.0, 0.0)
                    } else {
                        task2.cancel()
                    }
                }, 5L, 1L)
            }
        }, 0L, 1L)
    }
}