package cc.dreamcode.template

import cc.dreamcode.command.bukkit.BukkitCommandProvider
import cc.dreamcode.menu.bukkit.BukkitMenuProvider
import cc.dreamcode.menu.bukkit.serializer.MenuBuilderSerializer
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
import cc.dreamcode.template.command.ExampleCommand
import cc.dreamcode.template.command.handler.InvalidInputHandlerImpl
import cc.dreamcode.template.command.handler.InvalidPermissionHandlerImpl
import cc.dreamcode.template.command.handler.InvalidSenderHandlerImpl
import cc.dreamcode.template.command.handler.InvalidUsageHandlerImpl
import cc.dreamcode.template.command.result.BukkitNoticeResolver
import cc.dreamcode.template.config.MessageConfig
import cc.dreamcode.template.config.PluginConfig
import cc.dreamcode.template.nms.api.VersionProvider
import cc.dreamcode.template.profile.ProfileRepository
import eu.okaeri.configs.serdes.OkaeriSerdesPack
import eu.okaeri.configs.serdes.SerdesRegistry
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit
import eu.okaeri.persistence.document.DocumentPersistence
import eu.okaeri.tasker.bukkit.BukkitTasker
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta


class TemplatePlugin : DreamBukkitPlatform(), DreamBukkitConfig, DreamPersistence {

    override fun load(componentService: ComponentService) {
        templatePlugin = this
    }

    override fun enable(componentService: ComponentService) {
        componentService.isDebug = false

        this.registerInjectable(BukkitTasker.newPool(this))
        this.registerInjectable(BukkitMenuProvider.create(this))
        this.registerInjectable(BukkitNoticeProvider.create(this))

        this.registerInjectable(BukkitCommandProvider.create(this))
        componentService.registerExtension(DreamCommandExtension::class.java)

        this.registerInjectable(VersionProvider.versionAccessor)

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

        componentService.registerComponent(ProfileRepository::class)
        componentService.registerComponent(ExampleCommand::class)
    }

    override fun disable() {
        // features need to be call when server is stopping
    }

    override fun getDreamVersion(): DreamVersion {
        return DreamVersion.create("Dream-Template", "1.0-InDEV", "author")
    }

    override fun getConfigSerdesPack(): OkaeriSerdesPack {
        return OkaeriSerdesPack { registry: SerdesRegistry ->
            registry.register(BukkitNoticeSerializer())
            registry.register(MenuBuilderSerializer())
        }
    }

    override fun getPersistenceSerdesPack(): OkaeriSerdesPack {
        return OkaeriSerdesPack { registry ->
            registry.register(SerdesBukkit())

            registry.registerExclusive(ItemStack::class.java, ItemStackSerializer())
            registry.registerExclusive(ItemMeta::class.java, ItemMetaSerializer())
        }
    }

    companion object {
        lateinit var templatePlugin: TemplatePlugin
    }
}