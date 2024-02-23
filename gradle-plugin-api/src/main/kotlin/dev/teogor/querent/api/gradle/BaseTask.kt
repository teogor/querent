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

package dev.teogor.querent.api.gradle

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import dev.teogor.querent.api.models.PackageDetails
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory

/**
 * Abstract base class for code generation tasks.
 *
 * This abstract class provides common functionality for code
 * generation tasks, including input and output properties,
 * file generation methods, and type conversion methods.
 *
 * BaseTask provides the following features:
 *
 * * Input and output properties: BaseTask defines several input
 * and output properties that can be used to configure the code
 * generation process. These properties include the package name,
 * module name, output directory, package details, and type.
 *
 * * File generation methods: BaseTask provides two methods for generating
 * files: `fileSpec()` and `generate()`. The `fileSpec()` method generates
 * a file spec for the specified file name, and the `generate()` method
 * generates the code for the file spec.
 *
 * * Type conversion methods: BaseTask provides a `type()` method that
 * can be used to convert a package name and type name to a class name.
 * This method is useful for generating code that references classes
 * from other packages.
 *
 * To use BaseTask, you should extend it and implement the `doTask()`
 * method. The `doTask()` method is where you should perform the actual
 * code generation work.
 *
 * Example usage:
 *
 * ```kotlin
 * abstract class MyTask : BaseTask() {
 *
 *   override fun doTask() {
 *     // Perform code generation work here
 *   }
 * }
 *
 * class MyTaskImpl : MyTask() {
 *
 *   override fun doTask() {
 *     // Generate a file named "MyClass.kt"
 *     fileSpec("MyClass") {
 *       // Define the contents of the file here
 *     }
 *
 *     // Generate a file named "MyInterface.kt"
 *     fileSpec("MyInterface") {
 *       // Define the contents of the file here
 *     }
 *   }
 * }
 *
 * val myTask = MyTaskImpl()
 * myTask.execute()
 *
 *
 * This code will generate two files: `MyClass.kt` and `MyInterface.kt`.
 * The contents of the files will be defined by the `fileSpec()` blocks.
 */
abstract class BaseTask : DefaultTask() {

  /**
   * Gets or sets the package name for the generated code.
   *
   * The package name is the name of the package that the generated
   * code will be placed in. The default value for the package name
   * is the one of the project.
   */
  @get:Input
  @get:Optional
  abstract val packageName: Property<String>

  /**
   * Gets or sets the module name for the generated code.
   *
   * The module name is the name of the Gradle module that the
   * generated code will be placed in. The default value for the
   * module name is the name of the project.
   */
  @get:Input
  @get:Optional
  abstract val moduleName: Property<String>

  /**
   * Gets or sets the output directory for the generated code.
   *
   * The output directory is the directory where the generated code
   * will be placed. The default value for the output directory is
   * the `build/generated/querent` directory of the project.
   */
  @get:OutputDirectory
  @get:Optional
  abstract val outputDir: DirectoryProperty

  /**
   * Gets or sets the package details for the generated code.
   *
   * The package details include the package name and namespace
   * for the generated code. The default value for the package
   * details is the package name and namespace of the project.
   */
  @get:Input
  @get:Optional
  abstract val packageDetails: Property<PackageDetails>

  /**
   * Gets the package details for the generated code.
   *
   * The package details include the package name and namespace
   * for the generated code.
   */
  @get:Internal
  protected val type: PackageDetails
    get() = packageDetails.get()

  /**
   * Generates a file spec for the specified file name.
   *
   * @param fileName The name of the file to generate.
   * @param block A block of code that defines the contents of the file.
   */
  inline fun fileSpec(
    fileName: String,
    crossinline block: FileSpec.Builder.() -> Unit,
  ) {
    FileSpec.builder(packageName.get(), fileName)
      .apply(block)
      .build()
      .generate()
  }

  /**
   * Generates the code for the file spec.
   */
  fun FileSpec.generate() {
    writeTo(outputDir.get().asFile)
  }

  /**
   * Creates a class name for the specified type.
   *
   * @param packageName The package name of the type.
   * @param typeName The type name of the type.
   *
   * @return The class name for the specified type.
   */
  infix fun String.type(typeName: String): ClassName {
    return ClassName(this, typeName)
  }
}
