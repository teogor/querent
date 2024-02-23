[//]: # (This file was automatically generated - do not edit)

## Implementation

### Latest Version

The latest release is [`1.0.0-alpha01`](../releases.md)

### Releases

Here's a summary of the latest versions:

|    Version    |               Release Notes                | Release Date |
|:-------------:|:------------------------------------------:|:------------:|
| 1.0.0-alpha01 | [changelog 🔗](changelog/1.0.0-alpha01.md) | 27 Nov 2023  |

### Using Version Catalog

#### Declare Components

This catalog provides the implementation details of Querent libraries, including and individual
libraries, in TOML format.

=== "Default"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-querent = "1.0.0-alpha01"

    [libraries]
    teogor-querent-gradle-api = { module = "dev.teogor.querent:gradle-plugin-api", version.ref = "teogor-querent" }

    [plugins]
    teogor-querent = { id = "dev.teogor.querent", version.ref = "teogor-querent" }
    ```

#### Dependencies Implementation

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    plugins {
      alias(libs.plugins.teogor.querent)
    }

    dependencies {
      // Option - Not Required
      implementation(libs.teogor.querent.gradle.api)
    }
    ```

=== "Groovy"

    ```groovy title="build.gradle"
    apply plugin: libs.plugins.teogor.querent

    dependencies {
      // Option - Not Required
      implementation libs.teogor.querent.gradle.api
    }
    ```
