package dev.melodies.enchants

import dev.melodies.utils.EnchantProcChances
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object CustomEnchantsData {

    val FORTUNE = CustomEnchantData(
        Material.EMERALD_BLOCK,
        "Fortune",
        "<light_purple>Chance for extra blocks to drop</light_purple> <green>0.04% increase per level</green>",
        EnchantProcChances.fortuneProcPerLevel,
        NamespacedKey("lost-items", "fortune")
    )

    val EXPLOSIVE = CustomEnchantData(
        Material.TNT,
        "Explosive",
        "<light_purple>Cause a small explosion</light_purple> <green>0.04% increase per level</green>",
        EnchantProcChances.explosiveProcPerLevel,
        NamespacedKey("lost-items", "explosive")
    )

    val METEOR = CustomEnchantData(
        Material.MAGMA_BLOCK,
        "Meteor Rain",
        "<light_purple>Causes meteors to rain from the sky!</light_purple> <green>0.04% increase per level</green>",
        EnchantProcChances.meteorProcPerLevel,
        NamespacedKey("lost-items", "meteorrain")
    )

    val FLOW = CustomEnchantData(
        Material.SCULK,
        "Flow State",
        "<light_purple>Causes the user to enter the flow state for a period of time.</light_purple> <green>0.04% increase per level</green>",
        EnchantProcChances.flowProcPerLevel,
        NamespacedKey("lost-items", "flow")
    )

    val LIGHTNING = CustomEnchantData(
        Material.BEACON,
        "Flow State",
        "<light_purple>Causes lighting to strike around the user.</light_purple> <green>0.04% increase per level</green>",
        EnchantProcChances.lightningProcPerLevel,
        NamespacedKey("lost-items", "lightning")
    )

    val values = listOf(FORTUNE, EXPLOSIVE, METEOR, FLOW, LIGHTNING)
}

data class CustomEnchantData(
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
fun ItemStack.getCustomEnchantLevel(enchantment: CustomEnchantData): Int? {
    val data = this.itemMeta?.persistentDataContainer ?: return null
    return data.getOrDefault(enchantment.key, PersistentDataType.INTEGER, 0)
}

/**
 * Sets the level of a custom enchantment on this item.
 *
 * @param enchantment The custom enchantment to set the level of.
 * @param level The level to set the enchantment to.
 */
fun ItemStack.setCustomEnchantLevel(enchantment: CustomEnchantData, level: Int) {
    editMeta {
        val data = it.persistentDataContainer
        data.set(enchantment.key, PersistentDataType.INTEGER, level)
    }
}