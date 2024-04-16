/*
 * Copyright 2023 teogor (Teodor Grigor)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.diffplug.spotless.LineEnding
import com.vanniktech.maven.publish.SonatypeHost
import dev.teogor.winds.api.ArtifactIdFormat
import dev.teogor.winds.api.License
import dev.teogor.winds.api.NameFormat
import dev.teogor.winds.api.Person
import dev.teogor.winds.api.Scm
import dev.teogor.winds.api.TicketSystem
import dev.teogor.winds.api.model.DependencyType
import dev.teogor.winds.ktx.createVersion
import dev.teogor.winds.ktx.person
import dev.teogor.winds.ktx.scm
import dev.teogor.winds.ktx.ticketSystem
import org.jetbrains.dokka.gradle.DokkaPlugin

buildscript {
  repositories {
    google()
    mavenCentral()
  }
}

plugins {
  alias(libs.plugins.jetbrains.kotlin.jvm) apply true
  alias(libs.plugins.jetbrains.kotlin.serialization) apply false
  alias(libs.plugins.jetbrains.dokka) apply true
  alias(libs.plugins.jetbrains.api.validator) apply true
  alias(libs.plugins.teogor.winds) apply true
  alias(libs.plugins.spotless) apply true
  alias(libs.plugins.vanniktech.maven) apply true
  alias(libs.plugins.ben.manes.versions) apply true
  alias(libs.plugins.littlerobots.version.catalog.update) apply true
}

winds {
  windsFeatures {
    mavenPublishing = true
    docsGenerator = true
    workflowSynthesizer = true
  }

  moduleMetadata {
    name = "Querent"
    description = "\uD83C\uDFD7\uFE0F Querent lays the groundwork for your project's resource " +
      "management, fostering consistency and efficiency across your development workflow."

    yearCreated = 2023

    websiteUrl = "https://source.teogor.dev/querent/"
    apiDocsUrl = "https://source.teogor.dev/querent/html/"

    artifactDescriptor {
      group = "dev.teogor.querent"
      name = "Querent"
      nameFormat = NameFormat.FULL
      artifactIdFormat = ArtifactIdFormat.NAME_ONLY
      version = createVersion(1, 0, 0) {
        alphaRelease(3)
      }
    }

    // Providing SCM (Source Control Management)
    scm<Scm.GitHub> {
      owner = "teogor"
      repository = "querent"
    }

    // Providing Ticket System
    ticketSystem<TicketSystem.GitHub> {
      owner = "teogor"
      repository = "querent"
    }

    // Providing Licenses
    licensedUnder(License.Apache2())

    // Providing Persons
    person<Person.DeveloperContributor> {
      id = "teogor"
      name = "Teodor Grigor"
      email = "open-source@teogor.dev"
      url = "https://teogor.dev"
      roles = listOf("Code Owner", "Developer", "Designer", "Maintainer")
      timezone = "UTC+2"
      organization = "Teogor"
      organizationUrl = "https://github.com/teogor"
    }
  }

  publishingOptions {
    publish = false
    enablePublicationSigning = true
    optInForVanniktechPlugin = true
    cascadePublish = true
    sonatypeHost = SonatypeHost.S01
  }

  documentationBuilder {
    htmlPath = "html/"
    markdownNewlineSeparator = "  "
    dependencyGatheringType = DependencyType.NONE
  }
}

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
          .editorConfigOverride(
            mapOf(
              "ktlint_code_style" to "ktlint_official",
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

versionCatalogUpdate {
  keep {
    // keep versions without any library or plugin reference
    keepUnusedVersions = true
    // keep all libraries that aren't used in the project
    keepUnusedLibraries = true
    // keep all plugins that aren't used in the project
    keepUnusedPlugins = true
  }
}
