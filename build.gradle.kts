plugins {
    id("java")
    id("java-library")
    kotlin("jvm") version("2.2.0")

    id("dev.architectury.loom") version("1.9-SNAPSHOT") apply false
    id("architectury-plugin") version("3.4-SNAPSHOT") apply false
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    version = project.properties["mod_version"]!!
    group = project.properties["mod_group"]!!

    repositories {
        mavenCentral()
        maven("https://www.cursemaven.com")
        maven("https://api.modrinth.com/maven")
        maven("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
        maven("https://maven.impactdev.net/repository/development/")
        maven("https://maven.neoforged.net/releases")
        maven("https://thedarkcolour.github.io/KotlinForForge/")
    }

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }

    java {
        withSourcesJar()
    }
}

