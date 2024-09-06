rootProject.name = "lost-oasis"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

includeBuild("gradle-plugin")