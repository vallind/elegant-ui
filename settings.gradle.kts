pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "elegant-ui"
include(":elegant-ui")
include(":elegant-blur")
include(":showcase")
include(":sample")
include(":desktop-sample")
include(":web-sample")
