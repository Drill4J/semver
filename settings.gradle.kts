rootProject.name = "semver"

pluginManagement {

    val licenseVersion: String by extra
    plugins {
        id("com.github.hierynomus.license") version licenseVersion
        repositories {
            gradlePluginPortal()
        }
    }
}
