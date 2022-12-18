package io.github.andjsrk.hello

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Hello {
    fun hello() = "hello"
    fun helloItem() =
        ItemStack(Material.STICK).apply {
            itemMeta = itemMeta.apply {
                displayName(Component.text("hello"))
            }
        }
}
