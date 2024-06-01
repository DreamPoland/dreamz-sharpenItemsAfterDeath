package cc.dreamcode.template.command.result

import cc.dreamcode.command.DreamSender
import cc.dreamcode.command.bukkit.BukkitSender
import cc.dreamcode.command.result.ResultResolver
import cc.dreamcode.notice.adventure.BukkitNotice

class BukkitNoticeResolver : ResultResolver {
    override fun isAssignableFrom(type: Class<*>): Boolean {
        return BukkitNotice::class.java.isAssignableFrom(type)
    }

    override fun resolveResult(sender: DreamSender<*>, type: Class<*>, `object`: Any) {
        val bukkitSender = sender as BukkitSender
        val bukkitNotice = `object` as BukkitNotice

        bukkitNotice.send(bukkitSender.handler)
    }
}
