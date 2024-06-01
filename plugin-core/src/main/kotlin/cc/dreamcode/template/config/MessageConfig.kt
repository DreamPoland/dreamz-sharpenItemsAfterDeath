package cc.dreamcode.template.config

import cc.dreamcode.notice.adventure.BukkitNotice
import cc.dreamcode.notice.minecraft.NoticeType
import cc.dreamcode.platform.bukkit.component.configuration.Configuration
import eu.okaeri.configs.OkaeriConfig
import eu.okaeri.configs.annotation.*

@Configuration(child = "message.yml")
@Headers(
    Header("## Dream-Template (Message-Config) ##"),
    Header("Dostepne type: (DO_NOT_SEND, CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
)
class MessageConfig : OkaeriConfig() {

    @CustomKey("command-usage")
    var usage = BukkitNotice(NoticeType.CHAT, "&7Przyklady uzycia komendy: &c{label}")
    @CustomKey("command-usage-help")
    var usagePath = BukkitNotice(NoticeType.CHAT, "&f{usage} &8- &7{description}")

    @CustomKey("command-usage-not-found")
    var usageNotFound = BukkitNotice(NoticeType.CHAT, "&cNie znaleziono pasujacych do kryteriow komendy.")
    @CustomKey("command-path-not-found")
    var pathNotFound = BukkitNotice(NoticeType.CHAT, "&cTa komenda jest pusta lub nie posiadasz dostepu do niej.")
    @CustomKey("command-no-permission")
    var noPermission = BukkitNotice(NoticeType.CHAT, "&cNie posiadasz uprawnien.")
    @CustomKey("command-not-player")
    var notPlayer = BukkitNotice(NoticeType.CHAT, "&cTa komende mozna tylko wykonac z poziomu gracza.")
    @CustomKey("command-not-console")
    var notConsole = BukkitNotice(NoticeType.CHAT, "&cTa komende mozna tylko wykonac z poziomu konsoli.")
    @CustomKey("command-invalid-format")
    var invalidFormat = BukkitNotice(NoticeType.CHAT, "&cPodano nieprawidlowy format argumentu komendy. ({input})")

    @CustomKey("player-not-found")
    var playerNotFound = BukkitNotice(NoticeType.CHAT, "&cPodanego gracza nie znaleziono.")
    @CustomKey("world-not-found")
    var worldNotFound = BukkitNotice(NoticeType.CHAT, "&cPodanego swiata nie znaleziono.")
    @CustomKey("cannot-do-at-my-self")
    var cannotDoAtMySelf = BukkitNotice(NoticeType.CHAT, "&cNie mozesz tego zrobic na sobie.")
    @CustomKey("number-is-not-valid")
    var numberIsNotValid = BukkitNotice(NoticeType.CHAT, "&cPodana liczba nie jest cyfra.")

    @CustomKey("config-reloaded")
    var reloaded = BukkitNotice(NoticeType.CHAT, "&aPrzeladowano! &7({time})")
    @CustomKey("config-reload-error")
    var reloadError = BukkitNotice(NoticeType.CHAT, "&cZnaleziono problem w konfiguracji: &6{error}")

}