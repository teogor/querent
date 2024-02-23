[//]: # (This file was automatically generated - do not edit)

# Querent

Querent is a versatile and intuitive Sudoku puzzle generation library written in Kotlin. It
provides a comprehensive set of tools and algorithms for generating Sudoku puzzles of various grid
sizes and difficulty levels.

---

### API Reference

* [`dev.teogor.querent`](../html/){:target="_blank"}
* [`dev.teogor.querent:gradle-plugin`](../html/gradle-plugin){:target="_blank"}
* [`dev.teogor.querent:gradle-plugin-api`](../html/gradle-plugin-api){:target="_blank"}

### Release

|   Latest Update   | Stable Release | Beta Release | Alpha Release |
|:-----------------:|:--------------:|:------------:|:-------------:|
| February 23, 2023 |       -        |      -       | 1.0.0-alpha02 |
| November 27, 2023 |       -        |      -       | 1.0.0-alpha01 |

### Declaring dependencies

To add a dependency on Querent, you must add the Maven repository to your project.
Read [Maven's repository for more information](https://repo.maven.apache.org/maven2/).

Add the dependencies for the artifacts you need in the `build.gradle` file for your app or module:

=== "Kotlin"

    ```kotlin
    plugins {
      id("dev.teogor.querent") version "1.0.0-alpha02"
    }

    repositories {
      mavenCentral() // Add the repository where the library is hosted
    }

    dependencies {
      implementation("dev.teogor.querent:gradle-plugin-api:1.0.0-alpha02")
    }
    ```

=== "Groovy"

    ```groovy
    plugins {
      id 'dev.teogor.querent' version '1.0.0-alpha02'
    }

    repositories {
      mavenCentral() // Add the repository where the library is hosted
    }

    dependencies {
      implementation 'dev.teogor.querent:gradle-plugin-api:1.0.0-alpha02'
    }
    ```

### Feedback

Your feedback helps make Querent better. We want to know if you discover new issues or have ideas
for improving this library. Before creating a new issue, please take a look at
the [existing ones](https://github.com/teogor/querent) in this library. You can add your vote to
an
existing issue by clicking the star button.

[Create a new issue](https://github.com/teogor/querent/issues/new){ .md-button }

### Version 1.0.0

#### Version 1.0.0-alpha02

February 23, 2024

`dev.teogor.querent:querent-*:1.0.0-alpha02` is
released. [Version 1.0.0-alpha02 contains these commits.](https://github.com/teogor/querent/compare/1.0.0-alpha01...1.0.0-alpha02)

**Bug Fixes**

* Introduce dependency on generated compose resources task for preBuild
  phase ([#11](https://github.com/teogor/querent/pull/11)) by [@teogor](https://github.com/teogor)
* Introduce API level 26 requirements for `systemZoneOffset`
  and `buildLocalDateTime` ([#9](https://github.com/teogor/querent/pull/9))
  by [@teogor](https://github.com/teogor)

**Others**

* Migrate to Java 17 ([#6](https://github.com/teogor/querent/pull/6))
  by [@teogor](https://github.com/teogor)

#### Version 1.0.0-alpha01

November 27, 2023

`dev.teogor.querent:querent-*:1.0.0-alpha01` is
released. [Version 1.0.0-alpha01 contains these commits.](https://github.com/teogor/querent/commits/1.0.0-alpha01)

**Initial Release** 🎊
