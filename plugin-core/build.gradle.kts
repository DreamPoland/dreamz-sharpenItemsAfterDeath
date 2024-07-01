import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

repositories {
    maven("https://repo.codemc.io/repository/nms")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    // -- spigot api -- (base)
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")

    // -- dream-platform --
    implementation("cc.dreamcode.platform:core:1.12.4")
    implementation("cc.dreamcode.platform:core-kt:1.12.4")
    implementation("cc.dreamcode.platform:bukkit:1.12.4")
    implementation("cc.dreamcode.platform:bukkit-config:1.12.4")
    implementation("cc.dreamcode.platform:dream-command:1.12.4")

    // -- dream-utilities --
    implementation("cc.dreamcode:utilities:1.4.1")
    implementation("cc.dreamcode:utilities-bukkit:1.4.1")

    // -- dream-notice --
    implementation("cc.dreamcode.notice:core:1.5.1")
    implementation("cc.dreamcode.notice:minecraft:1.5.1")
    implementation("cc.dreamcode.notice:minecraft-adventure:1.5.5")
    implementation("cc.dreamcode.notice:bukkit-adventure:1.5.1")
    implementation("cc.dreamcode.notice:bukkit-adventure-serializer:1.5.1")

    // -- notice mini-messages --
    compileOnly("net.kyori:adventure-text-minimessage:4.16.0")

    // -- dream-command --
    implementation("cc.dreamcode.command:core:2.1.0")
    implementation("cc.dreamcode.command:bukkit:2.1.0")

    // -- configs--
    implementation("eu.okaeri:okaeri-configs-yaml-bukkit:5.0.1")
    implementation("eu.okaeri:okaeri-configs-serdes-bukkit:5.0.1")
    implementation("eu.okaeri:okaeri-configs-serdes-commons:5.0.1")

    // -- persistence data configure --
    implementation("eu.okaeri:okaeri-configs-json-gson:5.0.1")
    implementation("eu.okaeri:okaeri-configs-json-simple:5.0.1")

    // -- injector --
    implementation("eu.okaeri:okaeri-injector:2.1.0")

    // -- placeholders --
    implementation("eu.okaeri:okaeri-placeholders-core:5.0.1")

    // -- tasker (easy sync/async scheduler) --
    implementation("eu.okaeri:okaeri-tasker-bukkit:2.1.0-beta.3")
}

tasks.withType<ShadowJar> {
    archiveFileName.set("sharpenItemsAfterDeath-${project.version}.jar")

    relocate("com.cryptomorin", "dev.aporofobia.order.libs.com.cryptomorin")
    relocate("eu.okaeri", "dev.aporofobia.order.libs.eu.okaeri")
    relocate("net.kyori", "dev.aporofobia.order.libs.net.kyori")

    relocate("cc.dreamcode.platform", "dev.aporofobia.order.libs.cc.dreamcode.platform")
    relocate("cc.dreamcode.utilities", "dev.aporofobia.order.libs.cc.dreamcode.utilities")
    relocate("cc.dreamcode.menu", "dev.aporofobia.order.libs.cc.dreamcode.menu")
    relocate("cc.dreamcode.command", "dev.aporofobia.order.libs.cc.dreamcode.command")
    relocate("cc.dreamcode.notice", "dev.aporofobia.order.libs.cc.dreamcode.notice")

    relocate("org.bson", "dev.aporofobia.order.libs.org.bson")
    relocate("com.mongodb", "dev.aporofobia.order.libs.com.mongodb")
    relocate("com.zaxxer", "dev.aporofobia.order.libs.com.zaxxer")
    relocate("org.slf4j", "dev.aporofobia.order.libs.org.slf4j")
    relocate("org.json", "dev.aporofobia.order.libs.org.json")
    relocate("com.google.gson", "dev.aporofobia.order.libs.com.google.gson")

    relocate("kotlin", "dev.aporofobia.order.libs.kotlin")
    exclude("org/intellij/lang/annotations/**")
    exclude("org/jetbrains/annotations/**")
}