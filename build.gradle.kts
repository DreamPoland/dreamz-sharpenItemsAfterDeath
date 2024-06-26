import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.24"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.papermc.paperweight.userdev") version "1.7.1" apply false
}

allprojects {
    group = "dev.aporofobia.order"
    version = "1.0.1"

    apply(plugin = "kotlin")
    apply(plugin = "com.github.johnrengelman.shadow")

    repositories {
        mavenCentral()
        maven("https://repo.dreamcode.cc/releases")
        maven("https://storehouse.okaeri.eu/repository/maven-public")
    }
}

subprojects {
        java {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8

            withSourcesJar()
            withJavadocJar()
        }

        tasks.withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_1_8.toString()
                languageVersion = "1.8"
                javaParameters = true
            }
        }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.add("-parameters")
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.24")

        /* General */
        val lombok = "1.18.32"
        compileOnly("org.projectlombok:lombok:$lombok")
        annotationProcessor("org.projectlombok:lombok:$lombok")
        testCompileOnly("org.projectlombok:lombok:$lombok")
        testAnnotationProcessor("org.projectlombok:lombok:$lombok")
    }
}