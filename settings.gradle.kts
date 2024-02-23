pluginManagement {
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
    maven("https://androidx.dev/storage/compose-compiler/repository/")
  }
}

rootProject.name = "Querent"

include("gradle-plugin")
include("gradle-plugin-api")

// Pre-Built
includeBuild("examples")
