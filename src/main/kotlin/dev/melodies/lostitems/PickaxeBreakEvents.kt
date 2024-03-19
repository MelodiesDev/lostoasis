package dev.melodies.lostitems

import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import java.util.*

class PickaxeBreakEvents : Listener {
    /**
     * Map of online players to their boss bar and the number of blocks they've broken.
     */
    private val bossBars = mutableMapOf<UUID, BossBarEntry>()

    /**
     * The total number of blocks required to break to complete the boss bar.
     * Used to calculate the progress.
     */
    private val requiredBlocks = 100

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        // We only want to update the boss bar if the player broke the block
        // using the pickaxe.
        if (!event.player.persistentDataContainer.has(PickaxeGrantListener.KEY)) return

        // Get the boss bar entry for the player, or create a new one if it doesn't exist.
        // This will provide the initial styling, etc.
        val bossBarEntry = bossBars.getOrPut(event.player.uniqueId) {
            // Create the boss bar
            val bossBar = BossBar.bossBar(
                Component.text("Blocks Broken"),
                0.0f,
                BossBar.Color.BLUE,
                BossBar.Overlay.PROGRESS
            )

            // Show it to the player
            event.player.showBossBar(bossBar)

            // Create a new Pair(bossBar, 0) and return it
            BossBarEntry(bossBar)
        }

        // Increment the number of blocks broken
        val newBlocksBroken = bossBarEntry.blocksBroken + 1

        // Calculate the new progress from 0 to 1.
        // Adventure is AWESOME and, when any attribute of the boss bar is updated,
        // it will automatically update the boss bar for all viewers.
        bossBarEntry.bossBar.progress(newBlocksBroken.toFloat() / requiredBlocks)

        // Update the map entry with the new number of blocks broken
        bossBars[event.player.uniqueId] = bossBarEntry.copy(blocksBroken = newBlocksBroken)
    }

    private data class BossBarEntry(val bossBar: BossBar, val blocksBroken: Int = 0)
}