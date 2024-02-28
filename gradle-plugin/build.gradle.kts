plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    // TODO: remove once https://github.com/gradle/gradle/issues/15383#issuecomment-779893192 is fixed
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    // Plugin deps
    compileOnly(libs.paperweight)
    compileOnly(libs.plugin.yml)
    compileOnly(libs.run.paper)
}