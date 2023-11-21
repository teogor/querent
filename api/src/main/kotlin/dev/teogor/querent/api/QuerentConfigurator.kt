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

package dev.teogor.querent.api

/**
 * Interface for configuring Querent.
 *
 * This interface provides methods for configuring the built-in plugins
 * and setting project-specific options.
 */
interface QuerentConfigurator {

  /**
   * Access the `BuildFeatures` object for configuring build profile generation.
   */
  var buildFeatures: BuildFeatures

  /**
   * Configures the `BuildFeatures` object.
   *
   * @param action The configuration block for the `BuildFeatures` object.
   */
  fun buildFeatures(action: BuildFeatures.() -> Unit)

  /**
   * Access the `LanguagesSchema` object for configuring language schema generation.
   */
  var languagesSchemaOptions: LanguagesSchema

  /**
   * Configures the `LanguagesSchema` object.
   *
   * @param action The configuration block for the `LanguagesSchema` object.
   */
  fun languagesSchemaOptions(action: LanguagesSchema.() -> Unit)
}
