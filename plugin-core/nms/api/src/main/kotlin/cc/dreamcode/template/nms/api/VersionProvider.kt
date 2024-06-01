package cc.dreamcode.template.nms.api

import cc.dreamcode.utilities.ClassUtil
import java.util.concurrent.atomic.AtomicReference

object VersionProvider {
    val versionAccessor: VersionAccessor
        get() {
            val version = nmsVersion
            val className = "cc.dreamcode.template.nms." + version + "." + version.uppercase() + "_VersionAccessor"

            return ClassUtil.getClass(className)
                .map { versionClass ->
                    return@map versionClass.getConstructor().newInstance() as VersionAccessor
                }
                .orElseThrow { RuntimeException("Cannot find VersionAccessor for version $version") }
        }

    private val nmsVersion: String
        get() {
            val ref: AtomicReference<String> = AtomicReference<String>()

            for (pack in Package.getPackages()) {
                if (pack.name.startsWith("org.bukkit.craftbukkit.v")) {
                    val name = pack.name.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[3]

                    try {
                        Class.forName("org.bukkit.craftbukkit.$name.entity.CraftPlayer")
                        ref.set(name)
                    } catch (ignored: ClassNotFoundException) { }
                }
            }

            return ref.get() ?: throw RuntimeException("Cannot find server version")
        }
}
