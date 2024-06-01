package cc.dreamcode.template.command

import cc.dreamcode.command.CommandBase
import cc.dreamcode.command.annotation.*
import cc.dreamcode.notice.adventure.BukkitNotice
import cc.dreamcode.template.config.MessageConfig
import cc.dreamcode.template.config.PluginConfig
import cc.dreamcode.utilities.Formatter
import eu.okaeri.injector.annotation.Inject
import org.bukkit.command.CommandSender

@Command(name = "example")
class ExampleCommand @Inject constructor(
    private val pluginConfig: PluginConfig,
    private val messageConfig: MessageConfig
): CommandBase {

    @Async
    @Permission("dream-template.reload")
    @Executor(path = "reload", description = "Przeladowuje konfiguracje.")
    fun reload(sender: CommandSender) : BukkitNotice {
        val time = System.currentTimeMillis()

        return try {
            this.messageConfig.load()
            this.pluginConfig.load()

            this.messageConfig.reloaded
                .with("time", Formatter.format(System.currentTimeMillis() - time))
        }
        catch (e: Exception) {
            e.printStackTrace()

            this.messageConfig.reloadError
                .with("error", e.message ?: "?")
        }
    }
}
