import com.runemate.gradle.RuneMatePlugin
import java.net.URL

plugins {
    id("java")
    kotlin("jvm") version "2.0.20"
    id("com.runemate") version "1.4.1"
}

kotlin {
    jvmToolchain(17)
}

fun getLatestApiVersion(): String {
    val url = "https://gitlab.com/api/v4/projects/32972353/packages/maven/com/runemate/runemate-game-api/maven-metadata.xml"
    val content = URL(url).readText()
    return content.substringAfter("<latest>").substringBefore("</latest>")
}

fun getLatestClientVersion(): String {
    val url = "https://gitlab.com/api/v4/projects/10471880/packages/maven/com/runemate/runemate-client/maven-metadata.xml"
    val content = URL(url).readText()
    return content.substringAfter("<latest>").substringBefore("</latest>")
}

repositories {
    mavenCentral()
}

subprojects {
    apply<JavaPlugin>()
    apply<RuneMatePlugin>()


    runemate {
        devMode = true
        autoLogin = true
        allowExternalDependencies = true
    }

    tasks.runClient {
        enabled = false
    }
}

runemate {
    devMode = true
    autoLogin = true
    apiVersion = getLatestApiVersion()
    clientVersion = getLatestClientVersion()
    allowExternalDependencies = true
}

val superJar by tasks.registering(Jar::class) {
    group = "runemate"

    dependsOn(subprojects.map { it.tasks.generateManifests })
    dependsOn(subprojects.map { it.tasks.assemble })
    archiveBaseName.set("runemate-bots")
    subprojects.forEach {
        from(it.sourceSets.main.get().output)
        from(it.layout.buildDirectory.dir("runemate/sources/.runemate"))
    }
    from(layout.buildDirectory.dir("runemate/sources/.runemate"))
}

tasks.runClient {
    args("--debug")
    dependsOn(superJar)
}