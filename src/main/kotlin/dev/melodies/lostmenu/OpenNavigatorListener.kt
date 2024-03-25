package dev.melodies.lostmenu

import dev.melodies.utils.toMiniMessage
import dev.melodies.utils.wrapped
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem
import xyz.xenondevs.invui.window.Window

class OpenNavigatorListener : Listener {
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_AIR && event.action != Action.RIGHT_CLICK_BLOCK) return

        val item = event.item ?: return
        if (item.itemMeta?.persistentDataContainer?.has(CompassGrantListener.KEY) == false) return

        val gui = Gui.normal() // Creates the GuiBuilder for a normal GUI
            .setStructure(
                ". # . . . . . . .",
            )
            .addIngredient(
                '#', SimpleItem(
                    ItemBuilder(Material.LAPIS_BLOCK)
                        .setDisplayName(
                            "<gradient:blue:aqua>Far Shore</gradient>".toMiniMessage().wrapped()
                        )
                        .addLoreLines(
                            "<gradient:dark_purple:light_purple>Click to enter to the Far Shore</gradient>".toMiniMessage().wrapped()
                        )
                ) {
                    it.player.teleport(it.player.location.clone().add(it.player.location.direction.multiply(4)))
                    it.player.playSound(it.player, "minecraft:block.end_portal.spawn", 1.0f, 1.0f)
                    it.player.spawnParticle(org.bukkit.Particle.DRAGON_BREATH, it.player.location, 100, 1.0,0.0,1.0, 0.2)
                }
            )
            .build()

        val window = Window.single()
            .setViewer(event.player)
            .setTitle("Directory")
            .setGui(gui)
            .build()

        window.open()

    }
}
