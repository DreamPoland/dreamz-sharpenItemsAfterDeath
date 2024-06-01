package dev.aporofobia.order.command.handler

import cc.dreamcode.command.CommandInput
import cc.dreamcode.command.CommandMeta
import cc.dreamcode.command.DreamSender
import cc.dreamcode.command.bukkit.BukkitSender
import cc.dreamcode.command.handler.InvalidUsageHandler
import cc.dreamcode.utilities.builder.MapBuilder
import dev.aporofobia.order.config.MessageConfig
import eu.okaeri.injector.annotation.Inject
import java.util.*

class InvalidUsageHandlerImpl @Inject constructor(
    private val messageConfig: MessageConfig
) : InvalidUsageHandler {

    override fun handle(
        dreamSender: DreamSender<*>,
        optionalCommandMeta: Optional<CommandMeta>,
        commandInput: CommandInput
    ) {
        val bukkitSender = dreamSender as BukkitSender

        if (!optionalCommandMeta.isPresent) {
            this.messageConfig.usageNotFound.send(bukkitSender.handler)
            return
        }

        val commandMeta = optionalCommandMeta.get()
        val commandPathMetas = commandMeta.getFilteredCommandPaths(dreamSender)
        if (commandPathMetas.isEmpty()) {
            this.messageConfig.pathNotFound.send(
                bukkitSender.handler, MapBuilder<String, Any>()
                    .put("label", "/" + commandMeta.commandContext.name)
                    .put("description", commandMeta.commandContext.description)
                    .build()
            )

            return
        }

        this.messageConfig.usage.send(
            bukkitSender.handler, MapBuilder<String, Any>()
                .put("label", "/" + commandMeta.commandContext.name)
                .put("description", commandMeta.commandContext.description)
                .build()
        )

        for (commandPathMeta in commandPathMetas) {
            this.messageConfig.usagePath.send(
                bukkitSender.handler, MapBuilder<String, Any>()
                    .put("usage", commandPathMeta.usage)
                    .put("description", commandPathMeta.description)
                    .build()
            )
        }
    }
}
