import com.diffplug.spotless.LineEnding
import com.vanniktech.maven.publish.SonatypeHost
import dev.teogor.winds.api.MavenPublish
import dev.teogor.winds.api.getValue
import dev.teogor.winds.api.model.Developer
import dev.teogor.winds.api.model.LicenseType
import dev.teogor.winds.api.model.createVersion
import dev.teogor.winds.api.provider.Scm
import dev.teogor.winds.gradle.utils.afterWindsPluginConfiguration
import dev.teogor.winds.gradle.utils.attachTo
import org.gradle.internal.classpath.Instrumented.systemProperties
import org.jetbrains.dokka.gradle.DokkaPlugin

buildscript {
  repositories {
    google()
    mavenCentral()
  }
}

// Lists all plugins used throughout the project without applying them.
plugins {
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.kotlin.serialization) apply false

  alias(libs.plugins.winds) apply true
  alias(libs.plugins.vanniktech.maven) apply true
  alias(libs.plugins.dokka) apply true
  alias(libs.plugins.spotless) apply true
  alias(libs.plugins.api.validator) apply true
}

// Explicitly set the group and version for all subprojects
subprojects {
  group = "dev.teogor.querent"
  version = "1.0.0-alpha01"
}

winds {
  buildFeatures {
    mavenPublish = true

    docsGenerator = true
  }

  mavenPublish {
    displayName = "Querent"
    name = "querent"

    canBePublished = false

    description =
      "\uD83C\uDFD7\uFE0F Querent lays the groundwork for your project's resource management, fostering consistency and efficiency across your development workflow."

    groupId = "dev.teogor.querent"
    artifactIdElements = 1
    url = "https://source.teogor.dev/querent"

    version = createVersion(1, 0, 0) {
      alphaRelease(1)
    }

    // TODO winds
    //  required by dokka
    project.version = version!!.toString()

    inceptionYear = 2023

    sourceControlManagement(
      Scm.Git(
        owner = "teogor",
        repo = "querent",
      ),
    )

    addLicense(LicenseType.APACHE_2_0)

    addDeveloper(TeogorDeveloper())
  }

  docsGenerator {
    name = "Querent"
    identifier = "querent"
    alertOnDependentModules = true
  }
}

afterWindsPluginConfiguration { winds ->
  if (!plugins.hasPlugin("com.gradle.plugin-publish")) {
    val mavenPublish: MavenPublish by winds
    if (mavenPublish.canBePublished) {
      mavenPublishing {
        publishToMavenCentral(SonatypeHost.S01)
        signAllPublications()

        @Suppress("UnstableApiUsage")
        pom {
          coordinates(
            groupId = mavenPublish.groupId!!,
            artifactId = mavenPublish.artifactId!!,
            version = mavenPublish.version!!.toString(),
          )
          mavenPublish attachTo this
        }
      }
    }
  }
}

data class TeogorDeveloper(
  override val id: String = "teogor",
  override val name: String = "Teodor Grigor",
  override val email: String = "open-source@teogor.dev",
  override val url: String = "https://teogor.dev",
  override val roles: List<String> = listOf("Code Owner", "Developer", "Designer", "Maintainer"),
  override val timezone: String = "UTC+2",
  override val organization: String = "Teogor",
  override val organizationUrl: String = "https://github.com/teogor",
) : Developer

val ktlintVersion = "0.50.0"

val excludedProjects = listOf(
  project.name,
)

subprojects {
  if (!excludedProjects.contains(this.name)) {
    apply<com.diffplug.gradle.spotless.SpotlessPlugin>()
    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
      lineEndings = LineEnding.UNIX

      kotlin {
        target("**/*.kt")
        targetExclude("**/build/**/*.kt")
        ktlint(ktlintVersion)
          .userData(
            mapOf(
              "ktlint_code_style" to "official",
              "ij_kotlin_allow_trailing_comma" to "true",
              // These rules were introduced in ktlint 0.46.0 and should not be
              // enabled without further discussion. They are disabled for now.
              // See: https://github.com/pinterest/ktlint/releases/tag/0.46.0
              "disabled_rules" to
                "filename," +
                "annotation,annotation-spacing," +
                "argument-list-wrapping," +
                "double-colon-spacing," +
                "enum-entry-name-case," +
                "multiline-if-else," +
                "no-empty-first-line-in-method-block," +
                "package-name," +
                "trailing-comma," +
                "spacing-around-angle-brackets," +
                "spacing-between-declarations-with-annotations," +
                "spacing-between-declarations-with-comments," +
                "unary-op-spacing," +
                "no-trailing-spaces," +
                "no-wildcard-imports," +
                "max-line-length",
            ),
          )
        licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
        trimTrailingWhitespace()
        endWithNewline()
      }
      format("kts") {
        target("**/*.kts")
        targetExclude("**/build/**/*.kts")
        // Look for the first line that doesn't have a block comment (assumed to be the license)
        licenseHeaderFile(rootProject.file("spotless/copyright.kts"), "(^(?![\\/ ]\\*).*$)")
      }
      format("xml") {
        target("**/*.xml")
        targetExclude("**/build/**/*.xml")
        // Look for the first XML tag that isn't a comment (<!--) or the xml declaration (<?xml)
        licenseHeaderFile(rootProject.file("spotless/copyright.xml"), "(<[^!?])")
      }
    }
  }
}

apiValidation {
  /**
   * Subprojects that are excluded from API validation
   */
  ignoredProjects.addAll(excludedProjects)
}

subprojects {
  if (!excludedProjects.contains(project.name)) {
    apply<DokkaPlugin>()
  }
}
