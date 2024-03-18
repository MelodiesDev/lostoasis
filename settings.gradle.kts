rootProject.name = "lost-plugins"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

includeBuild("gradle-plugin")