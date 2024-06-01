package dev.aporofobia.order.controller

import dev.aporofobia.order.config.PluginConfig
import eu.okaeri.injector.annotation.Inject
import eu.okaeri.tasker.core.Tasker
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.ItemStack
import java.time.Duration

class PlayerDeathController @Inject constructor(
    private val tasker: Tasker,
    private val pluginConfig: PluginConfig
) : Listener {

    @EventHandler(priority = EventPriority.LOWEST)
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
        val itemsForKillerCount = (filteredDrops.size * percentage).toInt()

        // Items for the killer
        val itemsForKiller = filteredDrops.take(itemsForKillerCount)
        event.drops.addAll(itemsForKiller)

        // Add ignored items back to the drops for the killer
        event.drops.addAll(drops.filter { it.type in this.pluginConfig.ignoredItems })

        // Items for the victim
        val itemsForVictim = filteredDrops.drop(itemsForKillerCount)
        this.tasker.newDelayer(Duration.ofSeconds(2L))
            .forceIf { victim.health > 0 }
            .delayed {

                for (item in itemsForVictim) {
                    val remaining = victim.inventory.addItem(item).values
                    // Drop any remaining items that couldn't fit in the victim's inventory
                    for (remainingItem in remaining) {
                        victim.world.dropItemNaturally(victim.location, remainingItem)
                    }
                }

            }
            .executeSync()
    }



}