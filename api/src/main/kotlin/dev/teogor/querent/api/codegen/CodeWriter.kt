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

import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.provider.Provider

/**
 * Interface for writing resource files.
 *
 * This interface provides methods for writing resource files to
 * a specified output directory.
 */
interface CodeWriter {

  /**
   * Access the project associated with the code writer.
   */
  val project: Project

  /**
   * Access the source output directory provider.
   */
  val sourceOutputDir: Provider<Directory>

  /**
   * Access the intermediates output directory provider.
   */
  val intermediatesOutputDir: Provider<Directory>

  /**
   * Gets the output directory for the specified name.
   *
   * @param name The name of the output directory to retrieve.
   * @return The output directory for the specified name.
   */
  fun getOutputDir(name: String): Directory

  /**
   * Sets whether the code writer is enabled.
   *
   * @param enabled Whether the code writer is enabled.
   */
  fun setEnabled(enabled: Boolean)
}
