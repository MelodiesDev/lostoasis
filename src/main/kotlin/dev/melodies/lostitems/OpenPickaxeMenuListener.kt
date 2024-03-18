package dev.melodies.lostitems

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.window.Window


class OpenPickaxeMenuListener : Listener {

    @EventHandler
    fun rightClicked(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_AIR && event.action != Action.RIGHT_CLICK_BLOCK) return

        val item = event.item ?: return
        if (item.itemMeta?.persistentDataContainer?.has(PickaxeGrantListener.KEY) == false) return

        val gui = Gui.normal() // Creates the GuiBuilder for a normal GUI
            .setStructure(
                ". . . # & . . . .",
                ". . . . . . . . .",
                ". . . . . . . . .",
                ". . . . . . . . ."
            )
            .addIngredient('#', CustomEnchantMenuItem(CustomEnchantments.FORTUNE, item))
            .addIngredient('&', CustomEnchantMenuItem(CustomEnchantments.EXPLOSIVE, item))
            .build()


        val player = event.player

        val window = Window.single()
            .setViewer(player)
            .setTitle("InvUI")
            .setGui(gui)
            .build()

        window.open()
    }
}