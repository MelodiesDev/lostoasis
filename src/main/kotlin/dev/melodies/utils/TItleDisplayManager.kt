package dev.melodies.utils

import net.kyori.adventure.title.Title
import org.bukkit.FireworkEffect
import org.bukkit.entity.Firework
import org.bukkit.entity.Player

object TitleDisplayManager {

    val joinTitle = "Welcome to Lost Prison!".toMiniMessage()
    val joinSubtitle = "<gradient:aqua:dark_purple>hi :3</gradient>".toMiniMessage()

    fun handleLevelUp(player: Player, oldLevel: Int, newLevel: Int) {
        val currentSkill = "Mining"

        player.showTitle(
            Title.title(
                "<green>$currentSkill Up!".toMiniMessage(),
                "<yellow>$oldLevel -> $newLevel".toMiniMessage()
            )
        )

        player.world.spawn(player.location, Firework::class.java) { firework ->
            firework.ticksToDetonate = 30
            firework.fireworkMeta = firework.fireworkMeta.also { meta ->
                meta.power = 1
                meta.addEffect(
                    FireworkEffect.builder()
                        .with(FireworkEffect.Type.BALL_LARGE)
                        .withColor(org.bukkit.Color.BLUE, org.bukkit.Color.RED, org.bukkit.Color.YELLOW)
                        .flicker(true)
                        .trail(true)
                        .build()
                )
            }
        }
    }

}
