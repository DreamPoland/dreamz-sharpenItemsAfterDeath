package dev.aporofobia.order.controller

import dev.aporofobia.order.config.PluginConfig
import eu.okaeri.injector.annotation.Inject
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.ItemStack

class PlayerDeathController @Inject constructor(
    private val pluginConfig: PluginConfig
) : Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val victim = event.entity
        if (victim !is Player) {
            return
        }

        val drops: MutableList<ItemStack> = ArrayList(event.drops)
        event.drops.clear()

        val filteredDrops = drops.filter { it.type !in this.pluginConfig.ignoredItems }.toMutableList()
        filteredDrops.shuffle()

        val percentage = this.pluginConfig.dropPercentage / 100.0
        val itemsToDropCount = (filteredDrops.size * percentage).toInt()

        event.drops.addAll(filteredDrops.take(itemsToDropCount))
        event.drops.addAll(drops.filter { it.type in this.pluginConfig.ignoredItems })
    }

}