package dev.aporofobia.order.command.handler

import cc.dreamcode.command.DreamSender
import cc.dreamcode.command.bukkit.BukkitSender
import cc.dreamcode.command.handler.InvalidPermissionHandler
import cc.dreamcode.utilities.builder.MapBuilder
import dev.aporofobia.order.config.MessageConfig
import eu.okaeri.injector.annotation.Inject

class InvalidPermissionHandlerImpl @Inject constructor(
    private val messageConfig: MessageConfig
) : InvalidPermissionHandler {

    override fun handle(dreamSender: DreamSender<*>, permission: String) {
        val bukkitSender = dreamSender as BukkitSender

        this.messageConfig.noPermission.send(
            bukkitSender.handler, MapBuilder<String, Any>()
                .put("permission", permission)
                .build()
        )
    }
}
