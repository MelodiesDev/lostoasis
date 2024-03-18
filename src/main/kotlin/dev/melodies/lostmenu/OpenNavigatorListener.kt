package dev.melodies.lostmenu

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEvent
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem
import xyz.xenondevs.invui.window.Window

class OpenNavigatorListener : Listener {
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_AIR && event.action != Action.RIGHT_CLICK_BLOCK) return
        if (event.item?.type != Material.COMPASS || event.item?.itemMeta?.displayName() != MiniMessage.miniMessage().deserialize("<gradient:aqua:blue>Navigator</gradient>") ) return
        val gui = Gui.normal() // Creates the GuiBuilder for a normal GUI
            .setStructure(
                "# # # # # # # # #",
                "# . . . . . . . #",
                "# . . . . . . . #",
                "# # # # # # # # #"
            )
            .addIngredient('#', SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)))
            .build()

        val window = Window.single()
            .setViewer(event.player)
            .setTitle("Directory")
            .setGui(gui)
            .build()

        window.open()

    }
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.currentItem?.type == Material.COMPASS && event.currentItem?.itemMeta?.displayName() == MiniMessage.miniMessage().deserialize("<gradient:aqua:blue>Navigator</gradient>")) {
            event.isCancelled = true
        }
    }
}
