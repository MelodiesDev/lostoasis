package dev.melodies.enchants

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object CustomEnchantments {

    val FORTUNE = CustomEnchantment(
        Material.EMERALD_BLOCK,
        "Fortune",
        "<light_purple>Chance for extra blocks to drop</light_purple> <green>0.04% increase per level</green>",
        0.1,
        NamespacedKey("lost-items", "fortune")
    )

    val EXPLOSIVE = CustomEnchantment(
        Material.TNT,
        "Explosive",
        "<light_purple>Cause a small explosion</light_purple> <green>0.04% increase per level</green>",
        0.1,
        NamespacedKey("lost-items", "explosive")
    )

    val METEOR = CustomEnchantment(
        Material.MAGMA_BLOCK,
        "Meteor Rain",
        "<light_purple>Causes meteors to rain from the sky!</light_purple> <green>0.04% increase per level</green>",
        0.1,
        NamespacedKey("lost-items", "explosive")
    )

    val values = listOf(FORTUNE, EXPLOSIVE)
}

data class CustomEnchantment(
    val material: Material,
    val name: String,
    val description: String,
    val procPerLevel: Double,
    val key: NamespacedKey
)

/**
 * Gets the level of a custom enchantment on this item.
 *
 * @param enchantment The custom enchantment to get the level of.
 * @return The level of the enchantment, or null if the item does not have the enchantment.
 */
fun ItemStack.getCustomEnchantLevel(enchantment: CustomEnchantment): Int? {
    val data = this.itemMeta?.persistentDataContainer ?: return null
    return data.getOrDefault(enchantment.key, PersistentDataType.INTEGER, 0)
}

/**
 * Sets the level of a custom enchantment on this item.
 *
 * @param enchantment The custom enchantment to set the level of.
 * @param level The level to set the enchantment to.
 */
fun ItemStack.setCustomEnchantLevel(enchantment: CustomEnchantment, level: Int) {
    editMeta {
        val data = it.persistentDataContainer
        data.set(enchantment.key, PersistentDataType.INTEGER, level)
    }
}