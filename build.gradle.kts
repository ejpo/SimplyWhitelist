plugins {
    id("idea")
    kotlin("jvm") version "1.8.21"
    id("xyz.jpenilla.run-paper") version "2.1.0"
}

group = "io.ejpo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-beta.11")
    compileOnly("io.papermc.paper:paper-api:1.20-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
    runServer {
        // Configure the Minecraft version for our task.
        // This is the only required configuration besides applying the plugin.
        // Your plugin's jar (or shadowJar if present) will be used automatically.
        minecraftVersion("1.20.1")
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}