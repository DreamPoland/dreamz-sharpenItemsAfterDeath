package cc.dreamcode.template.command.handler

import cc.dreamcode.command.DreamSender
import cc.dreamcode.command.bukkit.BukkitSender
import cc.dreamcode.command.handler.InvalidSenderHandler
import cc.dreamcode.template.config.MessageConfig
import eu.okaeri.injector.annotation.Inject

class InvalidSenderHandlerImpl @Inject constructor(
    private val messageConfig: MessageConfig
): InvalidSenderHandler {

    override fun handle(dreamSender: DreamSender<*>, requireType: List<DreamSender.Type>) {
        val bukkitSender = dreamSender as BukkitSender

        if (requireType.contains(DreamSender.Type.CONSOLE)) {
            this.messageConfig.notConsole.send(bukkitSender.handler)
            return
        }

        if (requireType.contains(DreamSender.Type.CLIENT)) {
            this.messageConfig.notPlayer.send(bukkitSender.handler)
        }
    }
}
