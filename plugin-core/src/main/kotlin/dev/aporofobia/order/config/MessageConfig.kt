package dev.aporofobia.order.config

import cc.dreamcode.notice.adventure.BukkitNotice
import cc.dreamcode.notice.minecraft.NoticeType
import cc.dreamcode.platform.bukkit.component.configuration.Configuration
import eu.okaeri.configs.OkaeriConfig
import eu.okaeri.configs.annotation.CustomKey
import eu.okaeri.configs.annotation.Header
import eu.okaeri.configs.annotation.Headers

@Configuration(child = "message.yml")
@Headers(
    Header("## SharpenItemsAfterDeath (Message-Config) ##"),
    Header("Dostepne type: (DO_NOT_SEND, CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
)
data class MessageConfig(
    @CustomKey("command-usage")
    var usage: BukkitNotice = BukkitNotice(NoticeType.CHAT, "&7Przyklady uzycia komendy: &c{label}"),
    @CustomKey("command-usage-help")
    var usagePath: BukkitNotice = BukkitNotice(NoticeType.CHAT, "&f{usage} &8- &7{description}"),
    @CustomKey("command-usage-not-found")
    var usageNotFound: BukkitNotice = BukkitNotice(NoticeType.CHAT, "&cNie znaleziono pasujacych do kryteriow komendy."),
    @CustomKey("command-path-not-found")
    var pathNotFound: BukkitNotice = BukkitNotice(NoticeType.CHAT, "&cTa komenda jest pusta lub nie posiadasz dostepu do niej."),
    @CustomKey("command-no-permission")
    var noPermission: BukkitNotice = BukkitNotice(NoticeType.CHAT, "&cNie posiadasz uprawnien."),
    @CustomKey("command-not-player")
    var notPlayer: BukkitNotice = BukkitNotice(NoticeType.CHAT, "&cTa komende mozna tylko wykonac z poziomu gracza."),
    @CustomKey("command-not-console")
    var notConsole: BukkitNotice = BukkitNotice(NoticeType.CHAT, "&cTa komende mozna tylko wykonac z poziomu konsoli."),
    @CustomKey("command-invalid-format")
    var invalidFormat: BukkitNotice = BukkitNotice(NoticeType.CHAT, "&cPodano nieprawidlowy format argumentu komendy. ({input})"),
    @CustomKey("player-not-found")
    var playerNotFound: BukkitNotice = BukkitNotice(NoticeType.CHAT, "&cPodanego gracza nie znaleziono."),
    @CustomKey("config-reloaded")
    var reloaded: BukkitNotice = BukkitNotice(NoticeType.CHAT, "&aPrzeladowano! &7({time})"),
    @CustomKey("config-reload-error")
    var reloadError: BukkitNotice = BukkitNotice(NoticeType.CHAT, "&cZnaleziono problem w konfiguracji: &6{error}")
) : OkaeriConfig()