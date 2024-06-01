package dev.aporofobia.order.command

import cc.dreamcode.command.CommandBase
import cc.dreamcode.command.annotation.Async
import cc.dreamcode.command.annotation.Command
import cc.dreamcode.command.annotation.Executor
import cc.dreamcode.command.annotation.Permission
import cc.dreamcode.notice.adventure.BukkitNotice
import cc.dreamcode.utilities.Formatter
import dev.aporofobia.order.config.MessageConfig
import dev.aporofobia.order.config.PluginConfig
import eu.okaeri.injector.annotation.Inject
import org.bukkit.command.CommandSender

@Command(name = "sharpenitems")
class SharpenItemsCommand @Inject constructor(
    private val pluginConfig: PluginConfig,
    private val messageConfig: MessageConfig
) : CommandBase {

    @Async
    @Permission("sharpenitems.reload")
    @Executor(path = "reload", description = "Przeladowuje konfiguracje.")
    fun reload(sender: CommandSender): BukkitNotice {
        val time = System.currentTimeMillis()

        return try {
            this.messageConfig.load()
            this.pluginConfig.load()

            this.messageConfig.reloaded
                .with("time", Formatter.format(System.currentTimeMillis() - time))
        } catch (e: Exception) {
            e.printStackTrace()

            this.messageConfig.reloadError
                .with("error", e.message ?: "?")
        }
    }
}
