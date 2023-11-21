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

package dev.teogor.querent.api.codegen

import androidx.annotation.CallSuper
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.DynamicFeatureAndroidComponentsExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.api.variant.Variant
import dev.teogor.querent.api.utils.dir
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.get
import java.util.Locale

/**
 * Abstract base class for Querent blueprints.
 *
 * A blueprint is a class that defines how to generate code for a
 * specific purpose. Blueprints are used by Querent to generate code
 * for a variety of tasks, such as generating build profiles, XML
 * resources, and language schemas.
 *
 * The `Blueprint` class provides several methods that can be used to
 * define the code generation process. These methods include:
 *
 * * `isEnabled()`: This method returns a boolean value that indicates
 * whether the blueprint is enabled. If the blueprint is not enabled,
 * its code generation methods will not be called.
 *
 * * `packageNameSuffix`: This property specifies the package name
 * suffix for the generated code. If the property is not set, the
 * package name suffix will be empty.
 *
 * * `onCreate()`: This method is called when Querent creates a new
 * instance of the blueprint. The method is responsible for initializing
 * the blueprint's properties and applying any necessary configuration.
 *
 * * `apply()`: This method is called when Querent applies the blueprint.
 * The method is responsible for performing the code generation process.
 *
 * * `onVariants(variant: Variant)`: This method is called for each variant
 * of the project. The method is responsible for generating code for the
 * specified variant.
 *
 * To use the `Blueprint` class, you should extend it and implement the
 * `apply()` and `onVariants()` methods. The `apply()` method should perform
 * the code generation process, and the `onVariants()` method should generate
 * code for each variant of the project.
 *
 * Example usage:
 *
 * ```kotlin
 * class MyBlueprint(data: FoundationData) : Blueprint(data) {
 *
 *   override fun isEnabled(): Boolean = true
 *
 *   override fun packageNameSuffix(): String = "my"
 *
 *   override fun apply() {
 *     // Generate code here
 *   }
 *
 *   override fun onVariants(variant: Variant) {
 *     // Generate code for the specified variant here
 *   }
 * }
 *
 * val myBlueprint = MyBlueprint(FoundationData(project = project, codeWriter = codeWriter))
 * myBlueprint.onCreate()
 * myBlueprint.apply()
 *
 *
 * This code will instantiate a new instance of the `MyBlueprint` class
 * and call its `onCreate()`, `apply()`, and `onVariants()` methods.
 * The `apply()` method will generate code for the project, and the `onVariants()`
 * method will generate code for each variant of the project.
 */
abstract class Blueprint(
  data: FoundationData,
) {

  /**
   * Defines common properties and methods for Querent blueprints.
   *
   * Blueprints are used by Querent to generate code for a variety of
   * tasks, such as generating build profiles, XML resources, and language
   * schemas.
   *
   * This code provides several properties and methods that can be used
   * by blueprints to define and perform their code generation tasks.
   */
  protected val tag: String = this::class.java.simpleName

  /**
   * Provides a logger instance for the blueprint.
   *
   * The logger can be used to log messages about the blueprint's
   * activities.
   */
  protected val logger: Logger = Logging.getLogger(this::class.java)

  /**
   * Returns the name of the blueprint.
   *
   * The name of the blueprint is typically derived from the
   * class name of the blueprint implementation.
   */
  open val name: String = this::class.java.simpleName.replaceFirstChar {
    it.lowercase(Locale.ENGLISH)
  }

  /**
   * Accesses the project associated with the blueprint.
   *
   * The project provides access to the project's configuration,
   * such as the build script and Gradle extensions.
   */
  val project: Project = data.project

  /**
   * Accesses the code writer used by the blueprint.
   *
   * The code writer is used to generate code files.
   */
  private val codeWriter: CodeWriter = data.codeWriter

  /**
   * Returns the ApplicationAndroidComponentsExtension associated
   * with the project, if it exists.
   *
   * The ApplicationAndroidComponentsExtension provides access to
   * the configuration of the application module.
   */
  val applicationComponentsExtension: ApplicationAndroidComponentsExtension?
    get() = extension()

  /**
   * Returns the LibraryAndroidComponentsExtension associated with
   * the project, if it exists.
   *
   * The LibraryAndroidComponentsExtension provides access to the
   * configuration of the library module.
   */
  val libraryComponentsExtension: LibraryAndroidComponentsExtension?
    get() = extension()

  /**
   * Returns the DynamicFeatureAndroidComponentsExtension associated
   * with the project, if it exists.
   *
   * The DynamicFeatureAndroidComponentsExtension provides access to
   * the configuration of the dynamic feature module.
   */
  val dynamicFeatureComponentsExtension: DynamicFeatureAndroidComponentsExtension?
    get() = extension()

  /**
   * Indicates whether the blueprint is enabled.
   *
   * If the blueprint is not enabled, its code generation methods will
   * not be called.
   */
  open fun isEnabled(): Boolean = true

  /**
   * Returns the package name suffix for the generated code, if it exists.
   *
   * The package name suffix is appended to the package name of the project.
   */
  open val packageNameSuffix: String? = null

  /**
   * The package name for the generated code.
   *
   * The package name is the name of the Java package that the generated
   * code will be placed in.
   */
  lateinit var packageName: String

  /**
   * The namespace for the generated code.
   *
   * The namespace is the name of the XML namespace that the generated
   * code will be placed in.
   */
  lateinit var namespace: String

  /**
   * Returns the intermediates directory for the blueprint.
   *
   * The intermediates directory is the directory where the blueprint's
   * intermediate files are placed.
   */
  val intermediates: Directory
    get() = codeWriter.intermediatesOutputDir.dir(name)

  /**
   * The directory where the Kotlin source code for the blueprint is
   * generated.
   */
  lateinit var kotlinSources: Directory

  /**
   * The directory where the Java source code for the blueprint is
   * generated.
   */
  lateinit var javaSources: Directory

  /**
   * The directory where the Android resources for the blueprint are
   * generated.
   */
  lateinit var resSources: Directory

  /**
   * The directory where the Android resource files for the blueprint
   * are generated.
   */
  lateinit var resourcesSources: Directory

  /**
   * Called when Querent creates a new instance of the blueprint.
   *
   * The method is responsible for initializing the blueprint's properties
   * and applying any necessary configuration.
   */
  fun onCreate() {
    apply()

    applicationComponentsExtension?.apply {
      @Suppress("UnstableApiUsage")
      finalizeDsl {
        it.onFinalizeDsl()
      }

      onVariants {
        if (isEnabled()) {
          prepareDirectories(it.name)
          onVariants(it)
        }
      }
    }

    libraryComponentsExtension?.apply {
      @Suppress("UnstableApiUsage")
      finalizeDsl {
        it.onFinalizeDsl()
      }

      onVariants {
        if (isEnabled()) {
          prepareDirectories(it.name)
          onVariants(it)
        }
      }
    }

    dynamicFeatureComponentsExtension?.apply {
      @Suppress("UnstableApiUsage")
      finalizeDsl {
        it.onFinalizeDsl()
      }

      onVariants {
        if (isEnabled()) {
          prepareDirectories(it.name)
          onVariants(it)
        }
      }
    }
  }

  /**
   * Generates a directory for Kotlin source code for the specified variant.
   *
   * The directory is created in the project's source output directory,
   * under a subdirectory with the blueprint's name and the variant's name,
   * and then a subdirectory named "kotlin".
   *
   * @param variant The name of the variant for which to generate the directory.
   * @return The directory for the Kotlin source code for the specified variant.
   */
  fun kotlin(variant: String): Directory = codeWriter.sourceOutputDir
    .dir(name)
    .dir(variant)
    .dir("kotlin")

  /**
   * Generates a directory for Java source code for the specified variant.
   *
   * The directory is created in the project's source output directory,
   * under a subdirectory with the blueprint's name and the variant's name,
   * and then a subdirectory named "java".
   *
   * @param variant The name of the variant for which to generate the directory.
   * @return The directory for the Java source code for the specified variant.
   */
  fun java(variant: String): Directory = codeWriter.sourceOutputDir
    .dir(name)
    .dir(variant)
    .dir("java")

  /**
   * Generates a directory for Android resources for the specified variant.
   *
   * The directory is created in the project's source output directory, under
   * a subdirectory with the blueprint's name and the variant's name, and
   * then a subdirectory named "res".
   *
   * @param variant The name of the variant for which to generate the directory.
   * @return The directory for the Android resources for the specified variant.
   */
  fun res(variant: String): Directory = codeWriter.sourceOutputDir
    .dir(name)
    .dir(variant)
    .dir("res")

  /**
   * Generates a directory for Android resource files for the specified variant.
   *
   * The directory is created in the project's source output directory, under
   * a subdirectory with the blueprint's name and the variant's name, and then
   * a subdirectory named "resources".
   *
   * @param variant The name of the variant for which to generate the directory.
   * @return The directory for the Android resource files for the specified variant.
   */
  fun resources(variant: String): Directory = codeWriter.sourceOutputDir
    .dir(name)
    .dir(variant)
    .dir("resources")

  /**
   * Prepares the directories for the Kotlin, Java, Android resource, and Android
   * resource file sources for the specified variant.
   *
   * The directories are created in the project's source output directory, under
   * a subdirectory with the blueprint's name and the variant's name.
   *
   * @param variant The name of the variant for which to prepare the directories.
   */
  private fun prepareDirectories(variant: String) {
    kotlinSources = kotlin(variant)
    javaSources = java(variant)
    resSources = res(variant)
    resourcesSources = resources(variant)
  }

  /**
   * Called when the Gradle DSL for the ApplicationExtension, LibraryExtension,
   * or DynamicFeatureAndroidComponentsExtension is finalized.
   *
   * The method is responsible for enabling the code writer if the blueprint
   * is enabled, and setting the namespace and package name for the blueprint.
   */
  private fun CommonExtension<*, *, *, *, *>.onFinalizeDsl() {
    codeWriter.setEnabled(isEnabled())

    logger.quiet("[$tag] ApplicationExtension::${extension<ApplicationExtension>() == null}")
    extension<ApplicationExtension>()?.let { appExtension ->
      val namespace =
        appExtension.namespace ?: appExtension.defaultConfig.applicationId ?: "dev.teogor.ceres"
      val finalNamespace = if (packageNameSuffix != null) {
        "$namespace.$packageNameSuffix"
      } else {
        namespace
      }
      this@Blueprint.namespace = namespace
      this@Blueprint.packageName = finalNamespace
    }

    logger.quiet("[$tag] LibraryExtension::${extension<LibraryExtension>() == null}")
    extension<LibraryExtension>()?.let { libraryExtension ->
      val namespace = libraryExtension.namespace ?: "dev.teogor.ceres"
      val finalNamespace = if (packageNameSuffix != null) {
        "$namespace.$packageNameSuffix"
      } else {
        namespace
      }
      this@Blueprint.namespace = namespace
      this@Blueprint.packageName = finalNamespace
    }

    buildTypes.forEach {
      val variant = it.name
      sourceSets[variant].apply {
        kotlin.srcDirs(kotlin(variant))
        java.srcDirs(java(variant))
        res.srcDirs(res(variant))
        resources.srcDirs(resources(variant))
      }
    }

    finalizeDsl()
  }

  /**
   * Called when the Gradle DSL for the ApplicationExtension, LibraryExtension,
   * or DynamicFeatureAndroidComponentsExtension is finalized.
   *
   * The method is responsible for enabling the code writer if the blueprint
   * is enabled, and setting the namespace and package name for the blueprint.
   */
  open fun CommonExtension<*, *, *, *, *>.finalizeDsl() = Unit

  /**
   * Applies the blueprint to the project.
   *
   * The method is responsible for performing the code generation process.
   */
  open fun apply() = Unit

  /**
   * Called for each variant of the project.
   *
   * The method is responsible for generating code for the specified variant.
   *
   * @param variant The variant for which to generate code.
   */
  @CallSuper
  open fun onVariants(variant: Variant) = Unit

  /**
   * Retrieves the extension of the specified type from the project's extensions.
   *
   * @param T The type of the extension to retrieve.
   * @return The extension of the specified type, or null if no extension of
   * that type exists.
   */
  protected inline fun <reified T : Any> extension(): T? {
    return project.extensions.findByType<T>()
  }
}
