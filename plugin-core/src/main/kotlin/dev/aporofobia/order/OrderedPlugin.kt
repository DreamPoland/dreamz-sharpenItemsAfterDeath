package dev.aporofobia.order

import cc.dreamcode.command.bukkit.BukkitCommandProvider
import cc.dreamcode.notice.adventure.BukkitNoticeProvider
import cc.dreamcode.notice.adventure.serializer.BukkitNoticeSerializer
import cc.dreamcode.platform.DreamVersion
import cc.dreamcode.platform.bukkit.DreamBukkitConfig
import cc.dreamcode.platform.bukkit.DreamBukkitPlatform
import cc.dreamcode.platform.bukkit.component.ConfigurationResolver
import cc.dreamcode.platform.bukkit.serializer.ItemMetaSerializer
import cc.dreamcode.platform.bukkit.serializer.ItemStackSerializer
import cc.dreamcode.platform.component.ComponentService
import cc.dreamcode.platform.kotlin.registerComponent
import cc.dreamcode.platform.other.component.DreamCommandExtension
import cc.dreamcode.platform.persistence.DreamPersistence
import cc.dreamcode.platform.persistence.component.DocumentPersistenceResolver
import cc.dreamcode.platform.persistence.component.DocumentRepositoryResolver
import dev.aporofobia.order.command.SharpenItemsCommand
import dev.aporofobia.order.command.handler.InvalidInputHandlerImpl
import dev.aporofobia.order.command.handler.InvalidPermissionHandlerImpl
import dev.aporofobia.order.command.handler.InvalidSenderHandlerImpl
import dev.aporofobia.order.command.handler.InvalidUsageHandlerImpl
import dev.aporofobia.order.command.result.BukkitNoticeResolver
import dev.aporofobia.order.config.MessageConfig
import dev.aporofobia.order.config.PluginConfig
import eu.okaeri.configs.serdes.OkaeriSerdesPack
import eu.okaeri.configs.serdes.SerdesRegistry
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit
import eu.okaeri.persistence.document.DocumentPersistence
import eu.okaeri.tasker.bukkit.BukkitTasker
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class OrderedPlugin : DreamBukkitPlatform(), DreamBukkitConfig, DreamPersistence {

    override fun load(componentService: ComponentService) {
        orderedPlugin = this
    }

    override fun enable(componentService: ComponentService) {
        componentService.isDebug = false

        this.registerInjectable(BukkitTasker.newPool(this))
        this.registerInjectable(BukkitNoticeProvider.create(this))

        this.registerInjectable(BukkitCommandProvider.create(this))
        componentService.registerExtension(DreamCommandExtension::class.java)

        componentService.registerResolver(ConfigurationResolver::class.java)
        componentService.registerComponent(MessageConfig::class)

        componentService.registerComponent(BukkitNoticeResolver::class)
        componentService.registerComponent(InvalidInputHandlerImpl::class)
        componentService.registerComponent(InvalidPermissionHandlerImpl::class)
        componentService.registerComponent(InvalidSenderHandlerImpl::class)
        componentService.registerComponent(InvalidUsageHandlerImpl::class)

        componentService.registerComponent(PluginConfig::class) { pluginConfig: PluginConfig ->
            // register persistence + repositories
            this.registerInjectable(pluginConfig.storageConfig)

            componentService.registerResolver(DocumentPersistenceResolver::class.java)
            componentService.registerComponent(DocumentPersistence::class)
            componentService.registerResolver(DocumentRepositoryResolver::class.java)

            // enable additional logs and debug messages
            componentService.isDebug = pluginConfig.debug
        }

        componentService.registerComponent(SharpenItemsCommand::class)
    }

    override fun disable() {
        // features need to be call when server is stopping
    }

    override fun getDreamVersion(): DreamVersion {
        return DreamVersion.create("sharpenItemsAfterDeath", "1.0.1", "5star")
    }

    override fun getConfigSerdesPack(): OkaeriSerdesPack {
        return OkaeriSerdesPack { registry: SerdesRegistry -> registry.register(BukkitNoticeSerializer()) }
    }

    override fun getPersistenceSerdesPack(): OkaeriSerdesPack {
        return OkaeriSerdesPack { registry ->
            registry.register(SerdesBukkit())

            registry.registerExclusive(ItemStack::class.java, ItemStackSerializer())
            registry.registerExclusive(ItemMeta::class.java, ItemMetaSerializer())
        }
    }

    companion object {
        lateinit var orderedPlugin: OrderedPlugin
    }
}