package cc.dreamcode.template.config

import cc.dreamcode.platform.bukkit.component.configuration.Configuration
import cc.dreamcode.platform.persistence.StorageConfig
import eu.okaeri.configs.OkaeriConfig
import eu.okaeri.configs.annotation.*

@Configuration(child = "config.yml")
@Header("## Dream-Template (Main-Config) ##")
class PluginConfig : OkaeriConfig() {

    @Comment
    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    @CustomKey("debug")
    var debug = true

    @Comment
    @Comment("Uzupelnij dane do logowania bazy danych.")
    @CustomKey("storage-config")
    var storageConfig = StorageConfig("dreamtemplate")
}